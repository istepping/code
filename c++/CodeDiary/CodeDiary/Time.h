#include"Head.h"
#include"Year.h"
//ʱ���ѯ������
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
//���ݹ�����
class Diary {
private:
	int year;
	int month;
	int day;
public:
	Diary(int y, int m, int d);
	void switchYear();
};
