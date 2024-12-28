package com.example.file_watcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.Supplier;
import java.util.logging.Logger;

@SpringBootApplication
public class FileWatcherApplication {

	@Value("${file.watcher.path}")
	private String watchDirPath;

	private static final Logger logger = Logger.getLogger(FileWatcherApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(FileWatcherApplication.class, args);
	}

	@Bean
	public Supplier<Message<String>> customFileWatcher(StreamBridge streamBridge) throws IOException {
		Path watchDir = Paths.get(watchDirPath);
		WatchService watchService = watchDir.getFileSystem().newWatchService();
		watchDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

		return () -> {
			while (true) {
				try {
					WatchKey key = watchService.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
							Path relativePath = (Path) event.context();
							Path absolutePath = watchDir.resolve(relativePath);
							String content = readFileContent(absolutePath);
							logger.info("File changed: " + absolutePath + ", Content: " + content);
							Message<String> message = MessageBuilder.withPayload(content).build();
							streamBridge.send("customFileWatcher-out-0", message);
						}
					}
					if (!key.reset()) {
						break;
					}
				} catch (InterruptedException e) {
					logger.severe("Error while watching directory: " + e.getMessage());
					Thread.currentThread().interrupt();
					break;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			try {
				watchService.close();
			} catch (IOException e) {
				logger.severe("Error closing WatchService: " + e.getMessage());
			}
			return null;
		};
	}

	private String readFileContent(Path filePath) throws IOException {
		return new String(Files.readAllBytes(filePath));
	}
}
