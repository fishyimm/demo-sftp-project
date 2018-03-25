package com.example.sftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SftpApplicationTests {

	@Test
	public void getFileFromSftp() {

		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;

		String host = "your host";
		String user = "your username";
		String password = "your password";
		int port = 22;

		String encoding = "TIS-620";
		String channel = "sftp";

		String workingDir = "PH";
		String filename = "xxxxxxx.txt";
		InputStream inputStream = null;
		
		try {
			jsch = new JSch();
			
			jsch.setConfig("StrictHostKeyChecking", "no");
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			
			System.out.println("Establishing Connection...");
			
			session.connect();
			
			System.out.println("Connection established.");
			System.out.println("Creating SFTP Channel.");

			sftpChannel = (ChannelSftp) session.openChannel(channel);
			sftpChannel.connect();
			
			System.out.println("SFTP Channel created.");

			sftpChannel.cd(workingDir);
			inputStream = sftpChannel.get(filename);

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, encoding));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Exception" + e);
		} finally {
			if (sftpChannel.isConnected()) {
				sftpChannel.disconnect();
				System.out.println("sftpChannel.disconnect");
			}
			if (session.isConnected()) {
				session.disconnect();
				System.out.println("session.disconnect");
			}
		}
	}

	@Test
	public void testUpload() {
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		
		String host = "your host";
		String user = "your username";
		String password = "your password";
		int port = 22;

		String encoding = "TIS-620";
		String channel = "sftp";

		String workingDir = "PH";
		String filename = "xxxxxxx.txt";
		
		try {
			jsch = new JSch();
			jsch.setConfig("StrictHostKeyChecking", "no");
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			System.out.println("Establishing Connection...");
	        session.connect();
	        System.out.println("Connection established.");
	        System.out.println("Creating SFTP Channel.");
	        
	        sftpChannel = (ChannelSftp) session.openChannel(channel);
	        sftpChannel.connect();
	        System.out.println("SFTP Channel created.");
	        File file = new File("C:\\Users\\User\\Desktop\\test.txt");
	        
	        sftpChannel.put(new FileInputStream(file), "PH/"+ file.getName());
	        System.out.println("File transfered successfully to host.");
	        
		} catch(Exception e) {
			System.out.println("Exception" + e);
		} finally {
			if(sftpChannel.isConnected() ) {
				sftpChannel.disconnect();
				System.out.println("sftpChannel.disconnect" );
			}
			if(session.isConnected()) {
				session.disconnect();
				System.out.println("session.disconnect" );
			}
		}
	}
}
