#include"Time.h"
#include"Head.h"
Time::Time()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	currentYear = nowTime.wYear;
	currentMonth = nowTime.wMonth;
	currentDay = nowTime.wDay;
}
void Time::outputToday()
{
	Diary diary(currentYear, currentMonth, currentDay);
}
void Time::outputFromTime()
{

}
Diary::Diary(int y, int m, int d)
{
	year = y;
	month = m;
	day = d;
	switchYear();
}
void Diary::switchYear()
{
	switch (year)
	{
	case 2018:
	{
		Year2018 year2018(year, month, day);
    }
	break;
	default:
		break;
	}
}