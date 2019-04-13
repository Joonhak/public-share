package org.slam.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileSendTest {

	@Autowired
	SessionFactory<FTPFile> sf;
	private List<File> fileList;
	
	@Before
	public void init() throws FileNotFoundException {
		File file1 = ResourceUtils.getFile(this.getClass().getResource("/image1.jpg"));
		File file2 = ResourceUtils.getFile(this.getClass().getResource("/image2.jpg"));
		File file3 = ResourceUtils.getFile(this.getClass().getResource("/image3.jpg"));
		File file4 = ResourceUtils.getFile(this.getClass().getResource("/image4.jpg"));
		File file5 = ResourceUtils.getFile(this.getClass().getResource("/image5.jpg"));
		fileList = List.of(file1, file2, file3, file4, file5);
	}
	
	@Test
	public void SESSION_BIG_FILE_TEST() throws FileNotFoundException {
		var file = ResourceUtils.getFile(this.getClass().getResource("/test-image.gif"));
		long startTime = System.currentTimeMillis();
		try (
				var session = sf.getSession();
				var in = new FileInputStream(file);
				var bin = new BufferedInputStream(in)
		) {
			session.append( bin, "/spring/integration/c-" + file.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("CLIENT SEND ( BIG FILE ) TIME : " + (endTime - startTime));
	}
	
	@Test
	public void SESSION_MULTI_FILE_SEND_TEST() {
		long startTime = System.currentTimeMillis();
		fileList.parallelStream().forEach( f -> {
			try (
					var session = sf.getSession();
					var in = new FileInputStream(f);
					var bin = new BufferedInputStream(in)
			) {
				session.append( bin, "/spring/integration/c-" + f.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		long endTime = System.currentTimeMillis();
		System.out.println("CLIENT SEND TIME : " + (endTime - startTime));
	}

	@Test
	public void DIR_EXISTS_TEST() {
		try ( var session = sf.getSession() ) {
			System.out.println(session.exists("share/book/12553")); // Read timeout exception!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	@Autowired
	private FtpGateway gw;

	@Test
	public void GATEWAY_BIG_FILE_TEST() throws FileNotFoundException {
		var file = ResourceUtils.getFile(this.getClass().getResource("/test-image.gif"));
		long startTime = System.currentTimeMillis();
		try (
				var in = new FileInputStream(file);
				var bin = new BufferedInputStream(in)
		) {
			gw.send( bin.readAllBytes(), "gw-" + file.getName(), "/spring/integration");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("GATEWAY SEND ( BIG FILE ) TIME : " + (endTime - startTime));
	}
	
	@Test
	public void GATEWAY_MULTI_FILE_SEND_TEST() {
		long startTime = System.currentTimeMillis();
		fileList.parallelStream().forEach( f -> {
			try (
					var in = new FileInputStream(f);
					var bin = new BufferedInputStream(in)
			) {
				gw.send( bin.readAllBytes(), "gw-" + f.getName(), "/spring/integration");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		long endTime = System.currentTimeMillis();
		System.out.println("GATEWAY SEND TIME : " + (endTime - startTime));
	}
	*/
}
