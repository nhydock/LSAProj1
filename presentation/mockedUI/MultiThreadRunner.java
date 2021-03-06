package mockedUI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import mockedUI.UserThread;

/**
 * Creates a series of threads for a series of instruction files and runs them all in parallel
 * @author Merlin
 *
 */
public class MultiThreadRunner
{
	/**
	 * 
	 * @param args unused
	 * @throws FileNotFoundException if one of the files the user enters cannot be found
	 * @throws InterruptedException if we interrupt it
	 */
	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException
	{
		final ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<UserThread> uThreads = new ArrayList<UserThread>();
		Scanner inputScanner = new Scanner(System.in);
		String input = inputScanner.nextLine();
		String[] fileTitles = input.split(",");
		
		Runnable prepThread = null;
		for(String title:fileTitles)
		{
			System.out.println("Creating Thread for " + title.trim());
			UserThread target = new UserThread(title.trim());
			
			if (title.contains("init.txt"))
			{
				prepThread = new UserThread(title.trim());
			}
			else
			{
				uThreads.add(target);
				Thread t = new Thread(target);
				t.setName(title);
				threads.add(t);
			}
		}
		inputScanner.close();
		
		new Thread(prepThread){
			public void run() {
				//run prep thread first if it exists
				super.run();
				System.err.println("\nPreparation finished\n==================================\n");
				
				for (Thread t : threads)
				{
					t.start();
				}
				boolean running = true;
				while (running) {
					running = false;
					for (Thread t : threads) {
						running = running || t.isAlive();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.err.println("\n==================================\nFinished!");
			}
		}.start();
		
	
	}
}
