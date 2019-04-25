#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
#include<string.h>
#include<ctype.h>
#include<malloc.h>
#include<Windows.h>
#define N 10
int m = 0;
char order[15];
typedef struct hangxian
{
	char hangbanhao[15];//航班号
	char feijihao[15];//飞机号
	char qifeishijian[15];//起飞时间
	char zhongdianzhan[15];//降落地点
	struct hangxian *next;
}hangxian;
hangxian Han[N];
void xinxiluru();
void guanliyuan();
void client();
void lookInfo()
{
	int j;
	printf("航班号	      飞机号    起飞时间      降落地点  \n");
	for (j = 0; j<m; j++)
	{
		printf("%s\t%s\t%s\t\t%s", Han[j].hangbanhao, Han[j].feijihao, Han[j].qifeishijian, Han[j].zhongdianzhan);
		printf("\n");
	}
	system("pause");
}
void searchInfo()
{
	int i = 0;
	printf("订单查询中...\n");
	Sleep(1000);
	printf("订单获取中...\n");
	Sleep(1000);
	for (i = 0; i < m; i++)
	{
		if (strcmp(order, Han[i].hangbanhao) == 0)
		{
			printf("您的订单信息如下:\n");
			printf("航班号	      飞机号    起飞时间      降落地点  \n");
			printf("%s\t%s\t%s\t\t%s", Han[i].hangbanhao, Han[i].feijihao, Han[i].qifeishijian, Han[i].zhongdianzhan);
			printf("\n");
			system("pause");
			break;
		}
	}
	if (i == m)
	{
		printf("未查询到您输入的航班信息，请见检查输入是否正确\n");
		system("pause");
	}
}
void main()
{
	int n;
	while (1)
	{
		system("cls");
		printf("\n欢迎使用飞机航班信息查询与订票系统\n");
		printf("****************************************\n");
		printf("\t1.管理员登录\n");
		printf("\t2.客户登录\n");
		printf("\t3.退出系统\n");
		printf("****************************************\n");
		printf("请输入选择：");
		scanf("%d", &n);
		switch (n)
		{
		case 1:guanliyuan();//管理员
			break;
		case 2:client();//客户
			break;
		case 3:printf("感谢使用本系统，再见！\n");
			exit(0);
			break;
		default:printf("输入指令错误,请重新输入\n"); system("pause"); break;
		}
	}
}
void guanliyuan()
{
	int n;
	int q = 0;
	while (q == 0)
	{
		system("cls");
		printf("\n管理员登录界面\n");
		printf("****************************************\n");
		printf("\t1.信息录入\n");
		printf("\t2.航班查询\n");
		printf("\t3.退出系统\n");
		printf("****************************************\n");
		printf("请输入选择:");
		scanf("%d", &n);
		switch (n)
		{
		case 1:xinxiluru();//信息录入
			break;
		case 2:lookInfo();//查询功能
			break;
		case 3:
			printf("感谢使用本系统，再见！\n");
			q = 1;//退出
			break;
		default:printf("输入指令错误,请重新输入\n"); system("pause"); break;
		}
	}
}
void xinxiluru()
{
	int i = 0;
	FILE *fp;
	//printf("请输入文件名，路径:\n");
	fp = fopen("飞机航班.txt", "r");
	if (fp == NULL)
	{
		printf("Cannot open file!\n");
		exit(0);
	}
	//fscanf(fp, "%d", &m);
	//printf("航班号	      飞机号    起飞时间      降落地点  \n");
	//for (i = 0; i<m; i++)
	//{
	//	fscanf(fp, "%s%d%d%s", Han[i].hangbanhao, &Han[i].feijihao, &Han[i].qifeishijian, Han[i].zhongdianzhan);
	//	fclose(fp);
	//}
	while (fscanf(fp, "%s%s%s%s", &Han[i].hangbanhao, &Han[i].feijihao, &Han[i].qifeishijian, &Han[i].zhongdianzhan) != EOF)
	{
		i++;
		m++;
	}
	lookInfo();
	/*for (int j = 0; j<m; j++)
	{
	printf("%s\t%s\t%s\t\t%s", Han[j].hangbanhao, Han[j].feijihao, Han[j].qifeishijian, Han[j].zhongdianzhan);
	printf("\n");
	}*/
}
void orderFun()
{
	int j=0;
	printf("航班信息如下:\n");
	lookInfo();
	printf("请输入你要订购的航班号:");
	scanf("%s", &order);
	for (j = 0; j < m; j++)
	{
		if (strcmp(order, Han[j].hangbanhao) == 0)
		{
			printf("订单处理中.....\n");
			Sleep(2000);
			printf("订单生成中.....\n");
			Sleep(2000);
			printf("订单生成成功!\n");
			Sleep(1000);
			printf("订购成功!\n");
			Sleep(1000);
			printf("订单信息如下:\n");
			printf("航班号	      飞机号    起飞时间      降落地点  \n");
			printf("%s\t%s\t%s\t\t%s", Han[j].hangbanhao, Han[j].feijihao, Han[j].qifeishijian, Han[j].zhongdianzhan);
			printf("\n");
			system("pause");
			break;
		}
	}
	if (j == m)
	{
		printf("未查询到您输入的航班信息，请见检查输入是否正确\n");
		system("pause");
	}
}
void client()
{
	int n;
	int q = 0;
	while (q == 0)
	{
		system("cls");
		printf("\n客户登录界面\n");
		printf("****************************************\n");
		printf("\t1.在线订票\n");
		printf("\t2.航班查询\n");
		printf("\t3.退出系统\n");
		printf("****************************************\n");
		printf("请输入选择:");
		scanf("%d", &n);
		switch (n)
		{
		case 1:orderFun();
			break;
		case 2:searchInfo();
			break;
		case 3:
			printf("感谢使用本系统，再见！\n");
			q = 1;//退出
			break;
		default:printf("输入指令错误,请重新输入\n"); system("pause"); break;
		}
	}

}
1      张强    28           79          120        100
2      张鸣    88           78          98         105
3      陈坤    78           88          86         95
4      刘伟    91           55          86         78
5      姜刚    88           79          135        120
