
#include <stdio.h>
struct student
{
	long num;
	char name[10];
	float E;
	float P;
	float M;
	float Z;
	float sum;
	char pass;//是否通过
};
void Cal_Mark(student* stu1)
{
	int i;
	for (i = 0; i<3; i++)
	{
		stu1[i].sum = stu1[i].E + stu1[i].P + stu1[i].M + stu1[i].Z;
		if ((stu1[i].E >= 45) && (stu1[i].P >= 45) && (stu1[i].M >= 75) && (stu1[i].Z >= 75) && (stu1[i].sum >= 320))
			stu1[i].pass = 'p';
		else
			stu1[i].pass = 'f';
	}
}
void SelectionSort(student* stu1)
{
	for (int i = 0; i<3 - 1; i++)
		for (int j = 0; j < 3 - 1 - i; j++)
		{
			if (stu1[j].sum < stu1[j + 1].sum)
			{
				student temp;
				temp = stu1[j];
				stu1[j] = stu1[j + 1];
				stu1[j + 1] = temp;
			}
		}
}
int main()
{
	int i;
	student stu1[3];
	for (i = 0; i<3; i++)
	{
		printf("请输入考生考号、姓名、英语成绩、政治成绩、数学成绩、专业课成绩：");
		scanf("%ld%s%f%f%f%f", &stu1[i].num, &stu1[i].name, &stu1[i].E, &stu1[i].P, &stu1[i].M, &stu1[i].Z);

	}
	Cal_Mark(stu1);
	SelectionSort(stu1);
	printf("通过复试的学生名单：\n");
	for (i = 0; i<3; i++)
	{
		if (stu1[i].pass == 'p')
			printf("%ld %4s %4.2f %4.2f %4.2f %4.2f %4.2f %4c\n",stu1[i].num,stu1[i].name,stu1[i].E,stu1[i].M,stu1[i].P,stu1[i].Z,stu1[i].sum,stu1[i].pass);
	}
	return 0;
}
