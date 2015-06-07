package cn.javass.dp.proxy.example2;
/**
 * �������
 */
public class Proxy implements Subject{
	/**
	 * ���б�����ľ����Ŀ�����
	 */
	private RealSubject realSubject=null;
	/**
	 * ���췽�������뱻����ľ����Ŀ�����
	 * @param realSubject ������ľ����Ŀ�����
	 */
	public Proxy(RealSubject realSubject){
		this.realSubject = realSubject;
	}
	
	public void request() {
		//��ת�������Ŀ�����ǰ������ִ��һЩ���ܴ���
		//Ҫ��ӵĹ���1
		//ת�������Ŀ�����ķ���
		realSubject.request();
		//Ҫ��ӵĹ���2
		//��ת�������Ŀ�����󣬿���ִ��һЩ���ܴ���
	}

}
