package com.boccfc.liu.others;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @desc 
 * @author Liuweian
 * @createTime 2019年3月6日 下午3:55:47
 * @version 1.0.0
 */
public class BlockingQueueModel {
	
	private BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(8);
	
	class Product implements Runnable {
		
		public void run() {
			productor();
		}
		
		private void productor() {
			while(true) {
				try {
					queue.put(1);
				} catch (InterruptedException e) {
					System.out.println("新增失败！！！");
				}
				System.out.println("生产----：当前队列的长度为：" + queue.size());
				try {
					Thread.sleep(new Random().nextInt(10) * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Consum implements Runnable {

		public void run() {
			consumer();
		}
		
		private void consumer() {
			while(true) {
				try {
					queue.take();
				} catch (InterruptedException e) {
					System.out.println("移除失败");
				}
				
				System.out.println("消费----：当前队列的长度为：" + queue.size());

				try {
					Thread.sleep(new Random().nextInt(10) * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		BlockingQueueModel model = new BlockingQueueModel();
		Product product = model.new Product();
		Consum consum = model.new Consum();
		
		new Thread(product).start();
		new Thread(consum).start();
	}
}
