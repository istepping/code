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
		cout << "-微信小程序准备-"<<endl;
		cout << "<创建一个回调函数>callback WXarrangement" << endl;
	}
	void WXarrangement()
	{
		cout << "<WXarrangement被调用>" << endl;
		cout << "(1.网站报名做题通过 2.时间轴构建 3.项目内容交流)" << endl;
	}
};
