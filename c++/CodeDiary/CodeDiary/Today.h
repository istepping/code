#include"Head.h"
class Today20180426{
private:
	int year;
	int month;
	int day;
	int week;
public:
	Today20180426();
	void date(int y, int m, int d, int w) { year = y; month = m; day = d; week = w; }
};
class Today20180427 {
private:
	int year;
	int month;
	int day;
	int week;
public:
	Today20180427();
	void date(int y, int m, int d, int w) { year = y; month = m; day = d; week = w; }
	void diary1() {
		cout << "-΢��С����׼��-"<<endl;
		cout << "<����һ���ص�����>callback WXarrangement" << endl;
	}
	void WXarrangement()
	{
		cout << "<WXarrangement������>" << endl;
		cout << "(1.��վ��������ͨ�� 2.ʱ���ṹ�� 3.��Ŀ���ݽ���)" << endl;
	}
};
