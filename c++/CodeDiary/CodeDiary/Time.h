#include"Head.h"
#include"Year.h"
//时间查询管理类
class Time {
private:
	int currentYear;
	int currentMonth;
	int currentDay;
public:
	Time();
	void outputFromTime();
	void outputToday();
};
//内容管理类
class Diary {
private:
	int year;
	int month;
	int day;
public:
	Diary(int y, int m, int d);
	void switchYear();
};
