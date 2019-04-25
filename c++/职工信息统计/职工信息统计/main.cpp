#include "stdio.h"
#include "stdlib.h"
#include "string.h"
int saveflag = 0; /*是否需要存盘的标志变量*/
struct employee
{
	char name[15];
	char num[10];/* 工号 */
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
//Link l (注意是?字母 l 不是数字 1)
void Add(Link l);
void Disp(Link l); //查看职工所有信息
void Del(Link l); //删除功能
Node* Locate(Link l, char findmess[], char nameornum[]);
void Qur(Link l); //查询功能
void Tongji(Link l); //统计
void Sort(Link l); //排序
void Modify(Link l); //修改功能
void Save(Link l); //将单链表 l 中的数据写入文件
void printe(Node *p); //本函数用于打印链表中某个节点的数据内容 */
					  //以下 4 个函数用于输出中文标题
void printstart();
void Wrong();
void Nofind();
void printc();
void menu()
{
	printf("\t*****************************************************************\n");
	printf("\t**\n");
	printf("\t* 职工信息管理系统_结构体数组实现*\n");
	printf("\t**\n");
	printf("\t* [1] 增加职工信息 [2] 删除职工信息 *\n");
	printf("\t* [3] 查询职工信息 [4] 修改职工信息 *\n");
	printf("\t* [5] 插入职工记录 [6] 统计职工记录 *\n");
	printf("\t* [7] 排序 [8] 保存职工信息 *\n");
	printf("\t* [9] 显示数据 [0] 退出系统*\n");
	printf("\t**\n");
	printf("\t*****************************************************************\n");
} //void menu 菜单结束
void Disp(Link l) //显示单链表 l 中存储的职工记录?内容为 employee 结构中定义的内容
{
	int count = 0;
	Node *p;
	p = l->next; // l 存储的是单链表中头结点的指针?该头结点没有存储职工信息?指针域指向的后继结点才有职工信息
	if (!p) /*p==NULL,NUll 在 stdlib 中定义为 0*/
	{
		printf("\n=====>提示:没有职工记录可以显示!\n");
		return;
	}
	printf("\t\t\t\t 显示结果\n");
	printstart(); //打印横线
	printc(); //打印各学科标题
	printf("\n");
	while (p) //逐条输出链表中存储的职工信息
	{
		printe(p);
		p = p->next;
	}
	printstart();
	printf("\n");
} //void Disp 结束
void printstart()
{
	printf("-----------------------------------------------------------------------\n");
}
void Wrong()
{
	printf("\n=====>提示:输入错误!\n");
}
void Nofind()
{
	printf("\n=====>提示:没有找到该职工!\n");
}
void printc() /* 本函数用于输出中文 */
{
	printf(" 工号\t 姓名 性别 部门 职称 工资\n");
}
void printe(Node *p)/* 本函数用于打印链表中某个节点的数据内容 */
{
	printf("%s\t%s\t%s\t%s\t%s\t%d\n",
		p->data.num, p->data.name, p->data.sex, p->data.bm, p->data.zc, p->data.gz);
}
//Locate(l,findmess,"num");
/* 该函数用于定位连表中符合要求的结点?并返回该指针 */
Node* Locate(Link l, char findmess[], char zcornum[])
{
	Node *r=l;
	r = r->next;
	printf("查找方式为:%s\n", zcornum);
	if (strcmp(zcornum, "num") == 0) /* 按工号查询 */
	{
		while (r != NULL)
		{
			if (strcmp(r->data.num, findmess) == 0) /*若找到 findmess 值的工
													号*/
				return r;
			r = r->next;
		}
	}
	else if (strcmp(zcornum, "zc") == 0) /* 按职称查询 */
	{
		printf("职工查找中...\n");
		printf("输入姓名为:%s\n",findmess);
		while (r != NULL)
		{
			printf("正在匹配:%s\n",r->data.name);
			if (strcmp(r->data.name, findmess) == 0) /*若找到 findmess 值的职工职称*/
			{
				return r;
			}
			r = r->next;
		}
	}
	return 0; /*若未找到?返回一个空指针*/
}
//add()函数中,无节点时,r 指向 list 头,有节点时,r 指向末尾节点
void Add(Link l) /* 增加职工 */
{
	Node *p, *r, *s; /*实现添加操作的临时的结构体指针变量*/
	char num[10];
	int flag = 0;
	r = l;
	s = l->next; //链表没有节点时?s=null;/链表有节点时?指向第一个职工节点
	while (r->next != NULL) //如果存在后继结点时?r 指针后移一个
		r = r->next; /*将指针移至于链表最末尾?准备添加记录*/
	while (1)
	{
		printf("请你输入工号(以'0'返回上一级菜单:)");
		scanf("%s", num);
		if (strcmp(num, "0") == 0) //输入'0',跳出 while(1),即跳出 add()函数
			break;
		s = l->next; //作用? 每次从第一个节点开始找?看 num 是否重复。
		while (s) //工号重复时?返回主菜单
		{
			if (strcmp(s->data.num, num) == 0)
			{
				printf("=====>提示:工号为'%s'的职工已经存在,若要修改请你选择'4 修改'!\n", num);
				flag = 1;
				//break;
				return;
			}
			s = s->next;
		} //while(s)
		p = (Node *)malloc(sizeof(Node)); //生成没赋值的新节点 p
		strcpy(p->data.num, num);
		printf("请你输入姓名:");
		scanf("%s", p->data.name);
		printf("请你输入性别:");
		scanf("%s", p->data.sex);
		printf("请你输入职工所在部门:");
		scanf("%s", p->data.bm);
		printf("请你输入职工职称:");
		scanf("%s", p->data.zc);
		printf("请你输入职工工资:");
		scanf("%d", &p->data.gz);
		/* 信息输入已经完成 */
		p->next = NULL; /*表明这是链表的尾部结点*/
		r->next = p; /*将新建的结点加入链表尾部中*/
		r = p;
		saveflag = 1;
	} //while(1)
} //void Add 增加结束
void Del(Link l) /* 删除 */
{
	int sel;
	Node *p, *r; /*实现删除操作的临时的结构体指针变量*/
	char findmess[20];
	if (!l->next) //当 list 无后继结点时?提示和结束返回 del()
	{
		printf("\n=====>提示:没有记录可以删除!\n");
		return;
	}
	printf("\n=====>1 按工号删除\n=====>2 按姓名删除\n");
	scanf("%d", &sel);
	if (sel == 1) //按工号删除
	{
		printf("请你输入要删除的工号:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "num");
		if (p)
		{
			r = l;
			while (r->next != p)
				r = r->next; //从第一个结点找起?直到发现 r->next=p, 是待删除结点,跳出循环
			r->next = p->next; //r r->next(p) p->next
			free(p);
			printf("\n=====>提示:该职工已经成功删除!\n");
			saveflag = 1;
		}
		else
			Nofind(); //显示一句话
	} //if(sel==1)
	else if (sel == 2) //按姓名删除
	{
		printf("请你输入要删除的姓名:");
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
			printf("\n=====>提示:该职工已经成功删除!\n");
			saveflag = 1;
		}
		else
			Nofind();
	} //if(sel==2)
	else
		Wrong(); //显示输入错误的话
} //void Del 删除结束
void Qur(Link l) //查询功能
{
	int sel;
	char findmess[20];
	Node *p; //实现查询操作的临时的结构体指针变量
	if (!l->next)
	{
		printf("\n=====>提示:没有资料可以查询!\n");
		return;
	}
	printf("\n=====>1 按工号查找\n=====>2 按职称查找\n");
	scanf("%d", &sel);
	if (sel == 1)/* 工号 */
	{
		printf("请你输入要查找的工号:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "num");
		if (p)
		{
			printf("\t\t\t\t 查找结果\n");
			printstart(); //打印横线
			printc(); //打印各学科标题
			printe(p); //打印 p 结点各个数据成员的值
			printstart(); //打印横线
		}
		else
			Nofind();
	} //if(sel==1)
	else if (sel == 2) /* 职称 */
	{
		printf("请你输入要查找的职称:");
		scanf("%s", findmess);
		p = Locate(l, findmess, "zc");
		if (p)
		{
			printf("\t\t\t\t 查找结果\n");
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
} //void Qur 查询结束
void Modify(Link l) //修改功能
{
	Node *p;
	char findmess[20];
	if (!l->next)
	{
		printf("\n=====>提示:没有资料可以修改!\n");
		return;
	}
	printf("请你输入要修改的职工工号:");
	scanf("%s", findmess);
	p = Locate(l, findmess, "num");
	if (p)
	{
		printf("请你输入新工号(原来是%s):", p->data.num);
		scanf("%s", p->data.num);
		printf("请你输入新姓名(原来是%s):", p->data.name);
		scanf("%s", p->data.name);
		printf("请你输入新性别(原来是%s):", p->data.sex);
		scanf("%s", p->data.sex);
		printf("请你输入新的部门(原来是%s):", p->data.bm);
		scanf("%s", p->data.bm);
		printf("请你输入新的职称(原来是%s):", p->data.zc);
		scanf("%s",p->data.zc);
		printf("请你输入新的工资(原来是%d):", p->data.gz);
		scanf("%d", &p->data.gz);
		printf("\n=====>提示:资料修改成功!\n");
		//shoudsave=1;
	}
	else
		Nofind(); //if(p)结束
} //void Modify(Link l) //修改功能结束
  //插入记录:按工号查询到要插入的节点的位置?然后在该工号之后插入一个新节点。
void Insert(Link l)
{
	Node *s, *r, *p; /*p 指向插入位置?p 指新插入记录节点*/
	char ch, new_num[10], old_num[10];
	//old_num[]保存插入点位置之前的工号,new_num[]保存输入的新记录的工号
	int flag = 0;
	s = l->next;
	system("cls");
	Disp(l);
	while (1)
	{
		//stringinput(s,10,"please input insert location after theNumber:");
		printf("请你输入已存在的工号(以'0'返回上一级菜单:)");
		scanf("%s", old_num);
		if (strcmp(old_num, "0") == 0) //输入'0',跳出 while(1),即跳出Insert()函数
			return;
		s = l->next; //作用? 每次从第一个节点开始找
		flag = 0;
		while (s) /*查询该工号是否存在?flag=1 表示该工号存在*/
		{
			if (strcmp(s->data.num, old_num) == 0)
			{
				flag = 1;
				break;
			}
			s = s->next;
		}
		if (flag == 1)
			break; /*若工号存在?则进行插入之前的新记录的输入操作*/
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
			} //回主菜单
		}
	}//while(1)
	 /*以下新记录的插入新节点?工号不能跟已存在的工号相同?操作与 Add()
	 相同*/
	printf("请你输入待插入的工号(以'0'返回上一级菜单:)");
	scanf("%s", new_num);
	if (strcmp(new_num, "0") == 0) //输入'0',跳出 while(1),即跳出 add()函数
		return;
	s = l->next; //作用? 每次从第一个节点开始找?看 num 是否重复。
	while (s) //工号重复时?返回主菜单
	{
		if (strcmp(s->data.num, new_num) == 0)
		{
			printf("=====> 提 示 : 工 号 为 '%s' 的 职 工 已 经 存 在!\n", new_num);
			flag = 1;
			return;
		}
		s = s->next;
	} //while(s)
	p = (Node *)malloc(sizeof(Node));
	if (!p)
	{
		printf("\n allocate memory failure "); /*如没有申请到?打印
											   提示信息*/
		return; /*返回主界面*/
	}
	strcpy(p->data.num, new_num);
	printf("请你输入姓名:");
	scanf("%s", p->data.name);
	printf("请你输入性别:");
	scanf("%s", p->data.sex);
	printf("请你输入部门:");
	scanf("%s", p->data.bm);
	printf("请你输入职称:");
	scanf("%s", p->data.zc);
	printf("请你输入工资:");
	scanf("%d", &p->data.gz);
	// 信息输入已经完成
	p->next = NULL; /*表明这是链表的尾部结点*/
	saveflag = 1; /*在 main()有对该全局变量的判断?若为 1,则进行存盘操
				  作*/
				  /*将指针赋值给 r,因为 l 中的头节点的下一个节点才实际保存着学生的
				  记录*/
	r = l->next;
	while (1)
	{
		if (strcmp(r->data.num, old_num) == 0) /*在链表中插入一个节点*/
		{
			p->next = r->next;
			r->next = p;
			break;
		}
		r = r->next;
	}// while(1) ? r 作为查询指针?依次从第一个节点找起?找到后 跳出 while(1)循环
	Disp(l);
	printf("\n\n");
	// getchar();
}
void Tongji(Link l) //统计
{
	Node *max, *min;/*用于指向工资最高的节点*/
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
		printf("最高工资为?%d\n", max->data.gz);
		printf("\t%s\t%s\t%s\t%s\t%s\t%d\n\n", max->data.num, max->data.name, max->data.sex, max->data.bm, max->data.zc, max->data.gz);
		printf("最低工资为?%d\n", min->data.gz);
		printf("\t%s\t%s\t%s\t%s\t%s\t%d\n\n", min->data.num, min->data.name, min->data.sex, min->data.bm, min->data.zc, min->data.gz);
}
void Sort(Link l) //排序
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
	ll = (Node*)malloc(sizeof(Node)); /*用于创建新的节点*/
	if (!ll)
	{
		printf("\n allocate memory failure "); /*如没有申请到?打印
											   提示信息*/
		return; /*返回主界面*/
	}
	ll->next = NULL;
	system("cls");
	Disp(l); /*显示排序前的所有职工记录*/
	p = l->next;
	while (p) /*p!=NULL*/
	{
		s = (Node*)malloc(sizeof(Node)); /*新建节点用于保存从原链表中
										 取出的节点信息*/
		if (!s) /*s==NULL*/
		{
			printf("\n allocate memory failure "); /*如没有申请到?
												   打印提示信息*/
			return; /*返回主界面*/
		}
		s->data = p->data; /*填数据域*/
		s->next = NULL; /*指针域为空*/
		rr = ll;
		/*rr 链表于存储插入单个节点后保持排序的链表?ll 是这个链表的
		头指针,每次从头开始查找插入位置*/
		while (rr->next != NULL && rr->next->data.gz >= p->data.gz)
		{
			rr = rr->next;
		} /*指针移至总分比 p 所指的节点的总分小的节点位
		  置*/
		if (rr->next == NULL)/*若新链表 ll 中的所有节点的总分值都比
							 p->data.gz 大时?就将 p 所指节点加入链表尾部*/
			rr->next = s;
		else /*否则将该节点插入至第一个总分字段比它小的节点的前面*/
		{
			s->next = rr->next;
			rr->next = s;
		}
		p = p->next; /*原链表中的指针下移一个节点*/
	}
	l->next = ll->next; /*ll 中存储是的已排序的链表的头指针*/
	Disp(l);
	saveflag = 1;
	printf("\n =====>sort complete!\n");
}
void Save(Link l)
{
	FILE* fp;
	Node *p; //实现保存操作的临时的结构体指针变量
	int flag = 1, count = 0;
	fp = fopen("employee.txt", "wb");
	if (fp == NULL)
	{
		printf("\n=====>提示:重新打开文件时发生错误!\n");
		return;
	}
	p = l->next; //p 指向第一个记录结点
	while (p)
	{
		if (fwrite(p, sizeof(Node), 1, fp) == 1) //将第一个记录结点值写入文件
		{
			p = p->next; //依次写入第二个结点的值?
			count++; //文件的记录数+1
		}
		else
		{
			flag = 0;
			break;
		}
	} //while(p)
	if (count>0)
	{
		printf("\n=====> 提 示 : 文 件 保 存 成 功 .( 有 %d 条 记 录 已 经 保存.)\n", count);
		saveflag = 0;
	}
	else
	{
		system("cls");
		printf("保存文件失败?'0'条记录被保存?\n");
	}
	fclose(fp);
} // void Save 结束
void main()
{
	Link list; /*定义链表*/ // struct node *list;
	FILE *fp; /* 文件指针 */
	int choose; /*保存选择结果变量*/
	char ch; /*保存(y,Y,n,N)*/
	int count = 0; /*保存文件中的记录条数?或结点个数?*/
	struct node *p, *r; /*定义记录指针变量*/
	printf("\t\t\t\t 职工信息管理系统\n\t\t\t\t\n");
	list = (struct node*)malloc(sizeof(struct node));
	if (!list)
	{
		printf("\n allocate memory failure "); /*如没有申请到?打印
											   提示信息*/
		return; /*返回主界面*/
	}
	list->next = NULL;
	r = list;
	fp = fopen("employee.txt", "rb");
	if (fp == NULL)
	{
		printf("\n=====>提示:文件还不存在?是否创建?(y/n)\n");
		scanf("%c", &ch);
		if (ch == 'y' || ch == 'Y')
			fp = fopen("employee .txt", "ab+");
		else
			exit(0);
	} // if(fp==NULL)
	printf("\n=====>提示:文件已经打开,正在导入记录......\n");
	while (!feof(fp)) //没有到文件尾时?循环
	{
		p = (struct node*)malloc(sizeof(struct node));
		if (!p)
		{
			printf(" memory malloc failure!\n"); /*没有申请成功*/
			exit(0); /*退出*/
		}
		if (fread(p, sizeof(struct node), 1, fp)) /* 读文件的已有内容放
												  入结点中 */
		{
			p->next = NULL;
			r->next = p;
			r = p; /* 将该结点挂入链表中, r 指向最后的节点 */
			count++;
		}
	} //while(!feof(fp))
	fclose(fp); /* 关闭文件 */
	printf("\n=====>提示:记录导入完毕,共导入%d 条记录.\n", count);
	while (1)
	{
		menu();
		printf("\t\t====>请选择?");
		scanf("%d", &choose);
		if (choose == 0)
		{
			if (saveflag == 1)
			{
				getchar();
				printf("\n=====>提示:资料已经改动,是否将改动保存到文件中(y/n)?\n");
				scanf("%c", &ch);
				if (ch == 'y' || ch == 'Y')
					Save(list);
			} //if
			printf("\n=====>提示:你已经退出系统,再见!\n");
			break;
		}//if
		switch (choose)
		{
		case 1:Add(list);
			break; /* 增加职工记录 */
		case 2:
			Del(list);
			break;/* 删除职工记录 */
		case 3:
			Qur(list);
			break;/* 查询职工记录 */
		case 4:
			Modify(list);
			break;/* 修改职工记录 */
		case 5:
			Insert(list);
			break;/*插入职工记录*/
		case 6:
			Tongji(list);
			break;/*统计职工记录*/
		case 7:
			Sort(list);
			break;/*排序职工记录*/
		case 8:
			Save(list);
			break;/* 保存职工记录 */
		case 9:
			system("cls");
			Disp(list);
			break; /*显示职工记录*/
		default:
			Wrong();
			getchar();
			break;
		} //switch(choose)
	}//while(1)
} //main()
  /* */
