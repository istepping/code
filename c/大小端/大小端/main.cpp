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
		cout << "大端机器" << endl;
	}
	else if (myUnion.str == 0x04)
	{
		cout << "小端机器" << endl;
	}
	else
	{
		cout << "无法判断" << endl;
	}
}