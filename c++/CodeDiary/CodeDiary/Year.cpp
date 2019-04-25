#include"Year.h"
Year2018::Year2018(int y,int m,int d)
{
	year = y;
	month = m;
	day = d;
	switchMonth();
}
void Year2018::switchMonth()
{
	switch (month)
	{
	case 1:
		break;
	case 2:
		break;
	case 3:
		break;
	case 4:
	{
		Month201804 month201804(year, month, day);
	}
		break;
	case 5:
		break;
	case 6:
		break;
	default:
		break;
	}
}