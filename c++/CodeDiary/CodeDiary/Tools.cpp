#include"Tools.h"
void outputDate(int y, int m, int d, int w)
{
	cout << "��������" << y << "-" << m << "-" << d << "-��" << w << "��������" << endl;
}
int getYear()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wYear;
}
int getMonth()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wMonth;
}
int getDay()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wDay;
}
int getHour()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wHour;
}
int getMinute()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wMinute;
}
int getsecond()
{
	SYSTEMTIME nowTime;
	GetLocalTime(&nowTime);
	return nowTime.wSecond;
}