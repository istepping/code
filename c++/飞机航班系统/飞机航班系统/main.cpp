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
	char hangbanhao[15];//�����
	char feijihao[15];//�ɻ���
	char qifeishijian[15];//���ʱ��
	char zhongdianzhan[15];//����ص�
	struct hangxian *next;
}hangxian;
hangxian Han[N];
void xinxiluru();
void guanliyuan();
void client();
void lookInfo()
{
	int j;
	printf("�����	      �ɻ���    ���ʱ��      ����ص�  \n");
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
	printf("������ѯ��...\n");
	Sleep(1000);
	printf("������ȡ��...\n");
	Sleep(1000);
	for (i = 0; i < m; i++)
	{
		if (strcmp(order, Han[i].hangbanhao) == 0)
		{
			printf("���Ķ�����Ϣ����:\n");
			printf("�����	      �ɻ���    ���ʱ��      ����ص�  \n");
			printf("%s\t%s\t%s\t\t%s", Han[i].hangbanhao, Han[i].feijihao, Han[i].qifeishijian, Han[i].zhongdianzhan);
			printf("\n");
			system("pause");
			break;
		}
	}
	if (i == m)
	{
		printf("δ��ѯ��������ĺ�����Ϣ�������������Ƿ���ȷ\n");
		system("pause");
	}
}
void main()
{
	int n;
	while (1)
	{
		system("cls");
		printf("\n��ӭʹ�÷ɻ�������Ϣ��ѯ�붩Ʊϵͳ\n");
		printf("****************************************\n");
		printf("\t1.����Ա��¼\n");
		printf("\t2.�ͻ���¼\n");
		printf("\t3.�˳�ϵͳ\n");
		printf("****************************************\n");
		printf("������ѡ��");
		scanf("%d", &n);
		switch (n)
		{
		case 1:guanliyuan();//����Ա
			break;
		case 2:client();//�ͻ�
			break;
		case 3:printf("��лʹ�ñ�ϵͳ���ټ���\n");
			exit(0);
			break;
		default:printf("����ָ�����,����������\n"); system("pause"); break;
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
		printf("\n����Ա��¼����\n");
		printf("****************************************\n");
		printf("\t1.��Ϣ¼��\n");
		printf("\t2.�����ѯ\n");
		printf("\t3.�˳�ϵͳ\n");
		printf("****************************************\n");
		printf("������ѡ��:");
		scanf("%d", &n);
		switch (n)
		{
		case 1:xinxiluru();//��Ϣ¼��
			break;
		case 2:lookInfo();//��ѯ����
			break;
		case 3:
			printf("��лʹ�ñ�ϵͳ���ټ���\n");
			q = 1;//�˳�
			break;
		default:printf("����ָ�����,����������\n"); system("pause"); break;
		}
	}
}
void xinxiluru()
{
	int i = 0;
	FILE *fp;
	//printf("�������ļ�����·��:\n");
	fp = fopen("�ɻ�����.txt", "r");
	if (fp == NULL)
	{
		printf("Cannot open file!\n");
		exit(0);
	}
	//fscanf(fp, "%d", &m);
	//printf("�����	      �ɻ���    ���ʱ��      ����ص�  \n");
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
	printf("������Ϣ����:\n");
	lookInfo();
	printf("��������Ҫ�����ĺ����:");
	scanf("%s", &order);
	for (j = 0; j < m; j++)
	{
		if (strcmp(order, Han[j].hangbanhao) == 0)
		{
			printf("����������.....\n");
			Sleep(2000);
			printf("����������.....\n");
			Sleep(2000);
			printf("�������ɳɹ�!\n");
			Sleep(1000);
			printf("�����ɹ�!\n");
			Sleep(1000);
			printf("������Ϣ����:\n");
			printf("�����	      �ɻ���    ���ʱ��      ����ص�  \n");
			printf("%s\t%s\t%s\t\t%s", Han[j].hangbanhao, Han[j].feijihao, Han[j].qifeishijian, Han[j].zhongdianzhan);
			printf("\n");
			system("pause");
			break;
		}
	}
	if (j == m)
	{
		printf("δ��ѯ��������ĺ�����Ϣ�������������Ƿ���ȷ\n");
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
		printf("\n�ͻ���¼����\n");
		printf("****************************************\n");
		printf("\t1.���߶�Ʊ\n");
		printf("\t2.�����ѯ\n");
		printf("\t3.�˳�ϵͳ\n");
		printf("****************************************\n");
		printf("������ѡ��:");
		scanf("%d", &n);
		switch (n)
		{
		case 1:orderFun();
			break;
		case 2:searchInfo();
			break;
		case 3:
			printf("��лʹ�ñ�ϵͳ���ټ���\n");
			q = 1;//�˳�
			break;
		default:printf("����ָ�����,����������\n"); system("pause"); break;
		}
	}

}
1      ��ǿ    28           79          120        100
2      ����    88           78          98         105
3      ����    78           88          86         95
4      ��ΰ    91           55          86         78
5      ����    88           79          135        120
