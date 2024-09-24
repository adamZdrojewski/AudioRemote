package com.adam_z.AudioRemote;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AudioRemoteApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AudioRemoteApplication.class).headless(false).run(args);
	}

}
