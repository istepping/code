#include "stdio.h"
#include "stdlib.h"
#include "string.h"
int saveflag = 0; /*�Ƿ���Ҫ���̵ı�־����*/
struct employee
{
	char name[15];
	char num[10];/* ���� */
	char sex[4];
	char bm[15];
	char zc[20];
	int gz;
};
typedef struct node
{
	struct employee data;
	struct node *next;
}Node, *Link;
//Link l (ע����?��ĸ l �������� 1)
void Add(Link l);
void Disp(Link l); //�鿴ְ��������Ϣ
void Del(Link l); //ɾ������
Node* Locate(Link l, char findmess[], char nameornum[]);
void Qur(Link l); //��ѯ����
void Tongji(Link l); //ͳ��
void Sort(Link l); //����
void Modify(Link l); //�޸Ĺ���
void Save(Link l); //�������� l �е�����д���ļ�
void printe(Node *p); //���������ڴ�ӡ������ĳ���ڵ���������� */
					  //���� 4 ����������������ı���
void printstart();
void Wrong();
void Nofind();
void printc();
void menu()
{
	printf("\t*****************************************************************\n");
	printf("\t**\n");
	printf("\t* ְ����Ϣ����ϵͳ_�ṹ������ʵ��*\n");
	printf("\t**\n");
	printf("\t* [1] ����ְ����Ϣ [2] ɾ��ְ����Ϣ *\n");
	printf("\t* [3] ��ѯְ����Ϣ [4] �޸�ְ����Ϣ *\n");
	printf("\t* [5] ����ְ����¼ [6] ͳ��ְ����¼ *\n");
	printf("\t* [7] ���� [8] ����ְ����Ϣ *\n");
	printf("\t* [9] ��ʾ���� [0] �˳�ϵͳ*\n");
	printf("\t**\n");
	printf("\t*****************************************************************\n");
} //void menu �˵�����
void Disp(Link l) //��ʾ������ l �д洢��ְ����¼?����Ϊ employee �ṹ�ж��������
{
	int count = 0;
	Node *p;
	p = l->next; // l �洢���ǵ�������ͷ����ָ��?��ͷ���û�д洢ְ����Ϣ?ָ����ָ��ĺ�̽�����ְ����Ϣ
	if (!p) /*p==NULL,NUll �� stdlib �ж���Ϊ 0*/
	{
		printf("\n=====>��ʾ:û��ְ����¼������ʾ!\n");
		return;
	}
	printf("\t\t\t\t ��ʾ���\n");
	printstart(); //��ӡ����
	printc(); //��ӡ��ѧ�Ʊ���
	printf("\n");
	while (p) //������������д洢��ְ����Ϣ
	{
		printe(p);
		p = p->next;
	}
	printstart();
	printf("\n");
} //void Disp ����
void printstart()
{
	printf("-----------------------------------------------------------------------\n");
}
void Wrong()
{
	printf("\n=====>��ʾ:�������!\n");
}
void Nofind()
{
	printf("\n=====>��ʾ:û���ҵ���ְ��!\n");
}
void printc() /* ����������������� */
{
	printf(" ����\t ���� �Ա� ���� ְ�� ����\n");
}
void printe(Node *p)/* ���������ڴ�ӡ������ĳ���ڵ���������� */
{
	printf("%s\t%s\t%s\t%s\t%s\t%d\n",
		p->data.num, p->data.name, p->data.sex, p->data.bm, p->data.zc, p->data.gz);
}
//Locate(l,findmess,"num");
/* �ú������ڶ�λ�����з���Ҫ��Ľ��?�����ظ�ָ�� */
Node* Locate(Link l, char findmess[], char zcornum[])
{
	Node *r=l;
	r = r->next;
	printf("���ҷ�ʽΪ:%s\n", zcornum);
	if (strcmp(zcornum, "num") == 0) /* �����Ų�ѯ */
	{
		while (r != NULL)
		{
			if (strcmp(r->data.num, findmess) == 0) /*���ҵ� findmess ֵ�Ĺ�
													��*/
				return r;
			r = r->next;
		}
	}
	else if (strcmp(zcornum, "zc") == 0) /* ��ְ�Ʋ�ѯ */
	{
		printf("ְ��������...\n");
		printf("��������Ϊ:%s\n",findmess);
		while (r != NULL)
		{
			printf("����ƥ��:%s\n",r->data.name);
			if (strcmp(r->data.name, findmess) == 0) /*���ҵ� findmess ֵ��ְ��ְ��*/
			{
				return r;
			}
			r = r->next;
		}
	}
	return 0; /*��δ�ҵ�?����һ����ָ��*/
}
//add()������,�޽ڵ�ʱ,r ָ�� list ͷ,�нڵ�ʱ,r ָ��ĩβ�ڵ�
void Add(Link l) /* ����ְ�� */
{
	Node *p, *r, *s; /*ʵ����Ӳ�������ʱ�Ľṹ��ָ�����*/
	char num[10];
	int flag = 0;
	r = l;
	s = l->next; //����û�нڵ�ʱ?s=null;/�����нڵ�ʱ?ָ���һ��ְ���ڵ�
	while (r->next != NULL) //������ں�̽��ʱ?r ָ�����һ��
		r = r->next; /*��ָ��������������ĩβ?׼����Ӽ�¼*/
	while (1)
	{
		printf("�������빤��(��'0'������һ���˵�:)");
		scanf("%s", num);
		if (strcmp(num, "0") == 0) //����'0',���� while(1),������ add()����
			break;
		s = l->next; //����? ÿ�δӵ�һ���ڵ㿪ʼ��?�� num �Ƿ��ظ���
		while (s) //�����ظ�ʱ?�������˵�
		{
			if (strcmp(s->data.num, num) == 0)
			{
				printf("=====>��ʾ:����Ϊ'%s'��ְ���Ѿ�����,��Ҫ�޸�����ѡ��'4 �޸�'!\n", num);
				flag = 1;
				//break;
				return;
			}
			s = s->next;
		} //while(s)
		p = (Node *)malloc(sizeof(Node)); //����û��ֵ���½ڵ� p
		strcpy(p->data.num, num);
		printf("������������:");
		scanf("%s", p->data.name);
		printf("���������Ա�:");
		scanf("%s", p->data.sex);
		printf("��������ְ�����ڲ���:");
		scanf("%s", p->data.bm);
		printf("��������ְ��ְ��:");
		scanf("%s", p->data.zc);
		printf("��������ְ������:");
		scanf("%d", &p->data.gz);
		/* ��Ϣ�����Ѿ���� */
		p->next = NULL; /*�������������β�����*/
		r->next = p; /*���½��Ľ���������β����*/
		r = p;
		saveflag = 1;
	} //while(1)
} //void Add ���ӽ���
void Del(Link l) /* ɾ�� */
{
	int sel;
	Node *p, *r; /*ʵ��ɾ����������ʱ�Ľṹ��ָ�����*/
	char findmess[20];
	if (!l->next) //�� list �޺�̽��ʱ?��ʾ�ͽ������� del()
	{
		printf("\n=====>��ʾ:û�м�¼����ɾ��!\n");
		return;
	}
	printf("\n=====>1 ������ɾ��\n=====>2 ������ɾ��\n");
	scanf("%d", &sel);
	if (sel == 1) //������ɾ��
	{
		printf("��������Ҫɾ���Ĺ���:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "num");
		if (p)
		{
			r = l;
			while (r->next != p)
				r = r->next; //�ӵ�һ���������?ֱ������ r->next=p, �Ǵ�ɾ�����,����ѭ��
			r->next = p->next; //r r->next(p) p->next
			free(p);
			printf("\n=====>��ʾ:��ְ���Ѿ��ɹ�ɾ��!\n");
			saveflag = 1;
		}
		else
			Nofind(); //��ʾһ�仰
	} //if(sel==1)
	else if (sel == 2) //������ɾ��
	{
		printf("��������Ҫɾ��������:");
		scanf("%s", findmess);
		printf(findmess);
		Disp(l);
		p = Locate(l, findmess, "zc");
		if (p)
		{
			r = l;
			while (r->next != p)
				r = r->next;
			r->next = p->next; //r r->next(p) p->next
			free(p);
			printf("\n=====>��ʾ:��ְ���Ѿ��ɹ�ɾ��!\n");
			saveflag = 1;
		}
		else
			Nofind();
	} //if(sel==2)
	else
		Wrong(); //��ʾ�������Ļ�
} //void Del ɾ������
void Qur(Link l) //��ѯ����
{
	int sel;
	char findmess[20];
	Node *p; //ʵ�ֲ�ѯ��������ʱ�Ľṹ��ָ�����
	if (!l->next)
	{
		printf("\n=====>��ʾ:û�����Ͽ��Բ�ѯ!\n");
		return;
	}
	printf("\n=====>1 �����Ų���\n=====>2 ��ְ�Ʋ���\n");
	scanf("%d", &sel);
	if (sel == 1)/* ���� */
	{
		printf("��������Ҫ���ҵĹ���:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "num");
		if (p)
		{
			printf("\t\t\t\t ���ҽ��\n");
			printstart(); //��ӡ����
			printc(); //��ӡ��ѧ�Ʊ���
			printe(p); //��ӡ p ���������ݳ�Ա��ֵ
			printstart(); //��ӡ����
		}
		else
			Nofind();
	} //if(sel==1)
	else if (sel == 2) /* ְ�� */
	{
		printf("��������Ҫ���ҵ�ְ��:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "zc");
		if (p)
		{
			printf("\t\t\t\t ���ҽ��\n");
			printstart();
			printc();
			printe(p);
			printstart();
		}
		else
			Nofind();
	}
	else
		Wrong();
} //void Qur ��ѯ����
void Modify(Link l) //�޸Ĺ���
{
	Node *p;
	char findmess[20];
	if (!l->next)
	{
		printf("\n=====>��ʾ:û�����Ͽ����޸�!\n");
		return;
	}
	printf("��������Ҫ�޸ĵ�ְ������:");
	scanf("%s", findmess);
	p = Locate(l, findmess, "num");
	if (p)
	{
		printf("���������¹���(ԭ����%s):", p->data.num);
		scanf("%s", p->data.num);
		printf("��������������(ԭ����%s):", p->data.name);
		scanf("%s", p->data.name);
		printf("�����������Ա�(ԭ����%s):", p->data.sex);
		scanf("%s", p->data.sex);
		printf("���������µĲ���(ԭ����%s):", p->data.bm);
		scanf("%s", p->data.bm);
		printf("���������µ�ְ��(ԭ����%s):", p->data.zc);
		scanf("%s",p->data.zc);
		printf("���������µĹ���(ԭ����%d):", p->data.gz);
		scanf("%d", &p->data.gz);
		printf("\n=====>��ʾ:�����޸ĳɹ�!\n");
		//shoudsave=1;
	}
	else
		Nofind(); //if(p)����
} //void Modify(Link l) //�޸Ĺ��ܽ���
  //�����¼:�����Ų�ѯ��Ҫ����Ľڵ��λ��?Ȼ���ڸù���֮�����һ���½ڵ㡣
void Insert(Link l)
{
	Node *s, *r, *p; /*p ָ�����λ��?p ָ�²����¼�ڵ�*/
	char ch, new_num[10], old_num[10];
	//old_num[]��������λ��֮ǰ�Ĺ���,new_num[]����������¼�¼�Ĺ���
	int flag = 0;
	s = l->next;
	system("cls");
	Disp(l);
	while (1)
	{
		//stringinput(s,10,"please input insert location after theNumber:");
		printf("���������Ѵ��ڵĹ���(��'0'������һ���˵�:)");
		scanf("%s", old_num);
		if (strcmp(old_num, "0") == 0) //����'0',���� while(1),������Insert()����
			return;
		s = l->next; //����? ÿ�δӵ�һ���ڵ㿪ʼ��
		flag = 0;
		while (s) /*��ѯ�ù����Ƿ����?flag=1 ��ʾ�ù��Ŵ���*/
		{
			if (strcmp(s->data.num, old_num) == 0)
			{
				flag = 1;
				break;
			}
			s = s->next;
		}
		if (flag == 1)
			break; /*�����Ŵ���?����в���֮ǰ���¼�¼���������*/
		else
		{
			getchar();
			printf("\n=====>The number %s is not existing,try again?(y/n):", old_num);
			scanf("%c", &ch);
			if (ch == 'y' || ch == 'Y')
			{
				continue;
			}
			else
			{
				return;
			} //�����˵�
		}
	}//while(1)
	 /*�����¼�¼�Ĳ����½ڵ�?���Ų��ܸ��Ѵ��ڵĹ�����ͬ?������ Add()
	 ��ͬ*/
	printf("�������������Ĺ���(��'0'������һ���˵�:)");
	scanf("%s", new_num);
	if (strcmp(new_num, "0") == 0) //����'0',���� while(1),������ add()����
		return;
	s = l->next; //����? ÿ�δӵ�һ���ڵ㿪ʼ��?�� num �Ƿ��ظ���
	while (s) //�����ظ�ʱ?�������˵�
	{
		if (strcmp(s->data.num, new_num) == 0)
		{
			printf("=====> �� ʾ : �� �� Ϊ '%s' �� ְ �� �� �� �� ��!\n", new_num);
			flag = 1;
			return;
		}
		s = s->next;
	} //while(s)
	p = (Node *)malloc(sizeof(Node));
	if (!p)
	{
		printf("\n allocate memory failure "); /*��û�����뵽?��ӡ
											   ��ʾ��Ϣ*/
		return; /*����������*/
	}
	strcpy(p->data.num, new_num);
	printf("������������:");
	scanf("%s", p->data.name);
	printf("���������Ա�:");
	scanf("%s", p->data.sex);
	printf("�������벿��:");
	scanf("%s", p->data.bm);
	printf("��������ְ��:");
	scanf("%s", p->data.zc);
	printf("�������빤��:");
	scanf("%d", &p->data.gz);
	// ��Ϣ�����Ѿ����
	p->next = NULL; /*�������������β�����*/
	saveflag = 1; /*�� main()�жԸ�ȫ�ֱ������ж�?��Ϊ 1,����д��̲�
				  ��*/
				  /*��ָ�븳ֵ�� r,��Ϊ l �е�ͷ�ڵ����һ���ڵ��ʵ�ʱ�����ѧ����
				  ��¼*/
	r = l->next;
	while (1)
	{
		if (strcmp(r->data.num, old_num) == 0) /*�������в���һ���ڵ�*/
		{
			p->next = r->next;
			r->next = p;
			break;
		}
		r = r->next;
	}// while(1) ? r ��Ϊ��ѯָ��?���δӵ�һ���ڵ�����?�ҵ��� ���� while(1)ѭ��
	Disp(l);
	printf("\n\n");
	// getchar();
}
void Tongji(Link l) //ͳ��
{
	Node *max, *min;/*����ָ������ߵĽڵ�*/
	Node *t = l->next;
	if (!t)
	{
		system("cls");
		printf("\n=====>Not employee record!\n");
		getchar();
		return;
	}
	system("cls");
	Disp(l);
	max = min = t;
	while (t)
	{
		if (t->data.gz >= max->data.gz) max = t;
		if (t->data.gz <= min->data.gz) min = t;
		t = t->next;
	}
		printf("��߹���Ϊ?%d\n", max->data.gz);
		printf("\t%s\t%s\t%s\t%s\t%s\t%d\n\n", max->data.num, max->data.name, max->data.sex, max->data.bm, max->data.zc, max->data.gz);
		printf("��͹���Ϊ?%d\n", min->data.gz);
		printf("\t%s\t%s\t%s\t%s\t%s\t%d\n\n", min->data.num, min->data.name, min->data.sex, min->data.bm, min->data.zc, min->data.gz);
}
void Sort(Link l) //����
{
	Link ll;
	Node *p, *rr, *s;
	int i = 0;
	if (l->next == NULL)
	{
		system("cls");
		printf("\n=====>Not employee record!\n");
		getchar();
		return;
	}
	ll = (Node*)malloc(sizeof(Node)); /*���ڴ����µĽڵ�*/
	if (!ll)
	{
		printf("\n allocate memory failure "); /*��û�����뵽?��ӡ
											   ��ʾ��Ϣ*/
		return; /*����������*/
	}
	ll->next = NULL;
	system("cls");
	Disp(l); /*��ʾ����ǰ������ְ����¼*/
	p = l->next;
	while (p) /*p!=NULL*/
	{
		s = (Node*)malloc(sizeof(Node)); /*�½��ڵ����ڱ����ԭ������
										 ȡ���Ľڵ���Ϣ*/
		if (!s) /*s==NULL*/
		{
			printf("\n allocate memory failure "); /*��û�����뵽?
												   ��ӡ��ʾ��Ϣ*/
			return; /*����������*/
		}
		s->data = p->data; /*��������*/
		s->next = NULL; /*ָ����Ϊ��*/
		rr = ll;
		/*rr �����ڴ洢���뵥���ڵ�󱣳����������?ll ����������
		ͷָ��,ÿ�δ�ͷ��ʼ���Ҳ���λ��*/
		while (rr->next != NULL && rr->next->data.gz >= p->data.gz)
		{
			rr = rr->next;
		} /*ָ�������ֱܷ� p ��ָ�Ľڵ���ܷ�С�Ľڵ�λ
		  ��*/
		if (rr->next == NULL)/*�������� ll �е����нڵ���ܷ�ֵ����
							 p->data.gz ��ʱ?�ͽ� p ��ָ�ڵ��������β��*/
			rr->next = s;
		else /*���򽫸ýڵ��������һ���ܷ��ֶα���С�Ľڵ��ǰ��*/
		{
			s->next = rr->next;
			rr->next = s;
		}
		p = p->next; /*ԭ�����е�ָ������һ���ڵ�*/
	}
	l->next = ll->next; /*ll �д洢�ǵ�������������ͷָ��*/
	Disp(l);
	saveflag = 1;
	printf("\n =====>sort complete!\n");
}
void Save(Link l)
{
	FILE* fp;
	Node *p; //ʵ�ֱ����������ʱ�Ľṹ��ָ�����
	int flag = 1, count = 0;
	fp = fopen("employee.txt", "wb");
	if (fp == NULL)
	{
		printf("\n=====>��ʾ:���´��ļ�ʱ��������!\n");
		return;
	}
	p = l->next; //p ָ���һ����¼���
	while (p)
	{
		if (fwrite(p, sizeof(Node), 1, fp) == 1) //����һ����¼���ֵд���ļ�
		{
			p = p->next; //����д��ڶ�������ֵ?
			count++; //�ļ��ļ�¼��+1
		}
		else
		{
			flag = 0;
			break;
		}
	} //while(p)
	if (count>0)
	{
		printf("\n=====> �� ʾ : �� �� �� �� �� �� .( �� %d �� �� ¼ �� �� ����.)\n", count);
		saveflag = 0;
	}
	else
	{
		system("cls");
		printf("�����ļ�ʧ��?'0'����¼������?\n");
	}
	fclose(fp);
} // void Save ����
void main()
{
	Link list; /*��������*/ // struct node *list;
	FILE *fp; /* �ļ�ָ�� */
	int choose; /*����ѡ��������*/
	char ch; /*����(y,Y,n,N)*/
	int count = 0; /*�����ļ��еļ�¼����?�������?*/
	struct node *p, *r; /*�����¼ָ�����*/
	printf("\t\t\t\t ְ����Ϣ����ϵͳ\n\t\t\t\t\n");
	list = (struct node*)malloc(sizeof(struct node));
	if (!list)
	{
		printf("\n allocate memory failure "); /*��û�����뵽?��ӡ
											   ��ʾ��Ϣ*/
		return; /*����������*/
	}
	list->next = NULL;
	r = list;
	fp = fopen("employee.txt", "rb");
	if (fp == NULL)
	{
		printf("\n=====>��ʾ:�ļ���������?�Ƿ񴴽�?(y/n)\n");
		scanf("%c", &ch);
		if (ch == 'y' || ch == 'Y')
			fp = fopen("employee .txt", "ab+");
		else
			exit(0);
	} // if(fp==NULL)
	printf("\n=====>��ʾ:�ļ��Ѿ���,���ڵ����¼......\n");
	while (!feof(fp)) //û�е��ļ�βʱ?ѭ��
	{
		p = (struct node*)malloc(sizeof(struct node));
		if (!p)
		{
			printf(" memory malloc failure!\n"); /*û������ɹ�*/
			exit(0); /*�˳�*/
		}
		if (fread(p, sizeof(struct node), 1, fp)) /* ���ļ����������ݷ�
												  ������ */
		{
			p->next = NULL;
			r->next = p;
			r = p; /* ���ý�����������, r ָ�����Ľڵ� */
			count++;
		}
	} //while(!feof(fp))
	fclose(fp); /* �ر��ļ� */
	printf("\n=====>��ʾ:��¼�������,������%d ����¼.\n", count);
	while (1)
	{
		menu();
		printf("\t\t====>��ѡ��?");
		scanf("%d", &choose);
		if (choose == 0)
		{
			if (saveflag == 1)
			{
				getchar();
				printf("\n=====>��ʾ:�����Ѿ��Ķ�,�Ƿ񽫸Ķ����浽�ļ���(y/n)?\n");
				scanf("%c", &ch);
				if (ch == 'y' || ch == 'Y')
					Save(list);
			} //if
			printf("\n=====>��ʾ:���Ѿ��˳�ϵͳ,�ټ�!\n");
			break;
		}//if
		switch (choose)
		{
		case 1:Add(list);
			break; /* ����ְ����¼ */
		case 2:
			Del(list);
			break;/* ɾ��ְ����¼ */
		case 3:
			Qur(list);
			break;/* ��ѯְ����¼ */
		case 4:
			Modify(list);
			break;/* �޸�ְ����¼ */
		case 5:
			Insert(list);
			break;/*����ְ����¼*/
		case 6:
			Tongji(list);
			break;/*ͳ��ְ����¼*/
		case 7:
			Sort(list);
			break;/*����ְ����¼*/
		case 8:
			Save(list);
			break;/* ����ְ����¼ */
		case 9:
			system("cls");
			Disp(list);
			break; /*��ʾְ����¼*/
		default:
			Wrong();
			getchar();
			break;
		} //switch(choose)
	}//while(1)
} //main()
  /* */
