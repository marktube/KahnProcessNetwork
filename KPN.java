import java.util.LinkedList;
import java.util.Queue;

public class KPN {

	/** ALL COPYRIGHT RESERVED BY marktube
	 *Kahn Process Network
	 *Use 4 threads to implement the KPN sample
	 *2014.11.17
	 */

	static final public Queue<Object> h0 = new LinkedList<Object>();
	static final public Queue<Object> h1 = new LinkedList<Object>();
	static final public Queue<Object> f1 = new LinkedList<Object>();
	static final public Queue<Object> f0 = new LinkedList<Object>();
	static final public Queue<Object> g = new LinkedList<Object>();
	static boolean channel_f = false;
	static boolean channel_g = false;

	public KPN() {
		h0.offer(0);
		h1.offer(1);
		Thread_h1 newh1 = new Thread_h1();
		Thread thr_h1_1 = new Thread(newh1);
		Thread thr_h1_2 = new Thread(newh1);

		Thread_h0 newh0 = new Thread_h0();
		Thread thr_h0_1 = new Thread(newh0);
		Thread thr_h0_2 = new Thread(newh0);
		Thread thr_h0_3 = new Thread(newh0);

		Thread_f newf = new Thread_f();
		Thread thr_f1 = new Thread(newf);
		Thread thr_f2 = new Thread(newf);

		Thread_g newg = new Thread_g();
		Thread thr_g1 = new Thread(newg);
		Thread thr_g2 = new Thread(newg);
		
		thr_f1.start();
		thr_f2.start();
		thr_g1.start();
		thr_h1_1.start();
		thr_h0_1.start();
		thr_h1_2.start();
		thr_h0_2.start();
		thr_h0_3.start();
		thr_g2.start();
	}

	public static void main(String[] args) {
		new KPN();
	}

	class Thread_f implements Runnable {

		
		@Override
		public void run() {
			/*for (int i = 0; i < 100;) {
				if(send_f()){
					i++;
				}
			}*/
			while(true)
				send_f();
		}

		synchronized boolean send_f() {
			if (channel_f) {
				if (f1.isEmpty())
					return false;
				else {
					System.out.println(f1.peek().toString());
					g.offer(f1.poll());
					channel_f=!channel_f;
					return true;
				}
			} else {
				if (f0.isEmpty())
					return false;
				else {
					System.out.println(f0.peek().toString());
					g.offer(f0.poll());
					channel_f=!channel_f;
					return true;
				}
			}
		}

	}

	class Thread_g implements Runnable {

		@Override
		public void run() {
			/*for (int i = 0; i < 100;) {
				if (send_g()) {
					i++;
				}
			}*/
			while(true)
				send_g();
		}

		synchronized boolean send_g() {
			if (g.isEmpty())
				return false;
			else {
				if (channel_g)
					h1.offer(g.poll());
				else
					h0.offer(g.poll());
				channel_g=!channel_g;
				return true;
			}
		}

	}

	class Thread_h0 implements Runnable {

		@Override
		public void run() {
			/*for (int i = 0; i < 100;) {
				if (send_h0())
					i++;
			}*/
			while(true)
				send_h0();
		}

		synchronized boolean send_h0() {
			if (h0.isEmpty())
				return false;
			else {
				f0.offer(h0.poll());
				return true;
			}
		}
	}

	class Thread_h1 implements Runnable {

		@Override
		public void run() {
			/*for (int i = 0; i < 100;) {
				if (send_h1())
					i++;
			}*/
			while(true)
				send_h1();
		}

		synchronized boolean send_h1() {
			if (h1.isEmpty())
				return false;
			else {
				f1.offer(h1.poll());
				return true;
			}
		}
	}
}