#include<stdio.h>
#include<iostream>
using namespace std;
union MyUnion
{
	char str;
	int data;
}myUnion;
int main()
{
	myUnion.data = 0x01020304;
	if (myUnion.str == 0x01)
	{
		cout << "��˻���" << endl;
	}
	else if (myUnion.str == 0x04)
	{
		cout << "С�˻���" << endl;
	}
	else
	{
		cout << "�޷��ж�" << endl;
	}
}