#include"Month.h"
Month201804::Month201804(int y,int m,int d)
{
	year = y;
	month = m;
	day = d;
	switchDay();
}
void Month201804::switchDay()
{
	switch (day)
	{
	case 1:
		break;
	case 26:
	{
		Today20180426 day20180426;
	}
		break;
	case 27:
	{
		Today20180427 day20180427;
	}
	break;
	default:
		break;
	}
}