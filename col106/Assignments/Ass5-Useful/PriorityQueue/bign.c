#include<stdio.h>
#include<string.h>

extern int* sum();
extern int* subtract();

 int main()
{
	char num1[255],num2[255];
	printf("Enter two numbers:" );
	scanf("%s\n",num1);
	scanf("%s",num2);
   
    sum(num1,num2);




char numb1[255],numb2[255];
printf("Entetr two numbers to subtract:");
scanf("%s\n",numb1);
scanf("%s",numb2);
 subtract(numb1,numb2);

}

int* sum(char *a,char *b){//////////////////////////////////
	
	int sum[strlen(a)+strlen(b)+100];
	int carry=0;

	int arr1[strlen(a)+1],arr2[strlen(b)+1];
	
    
   int len1,len2;
for(len1=0;a[len1]!='\0'; len1++){
	arr1[len1] = a[len1]-'0';
}
arr1[strlen(a)]=0;

for(len2=0;b[len2]!='\0'; len2++){
	arr2[len2]=b[len2]-'0';
}
arr2[strlen(b)]=0;
	int i=len1-1;
	int j=len2-1;
	int k=0;

for(;i >=0,j>=0;i--,j--,k++){

	sum[k]=(arr1[i]+arr2[j]+carry)%10;
	carry=(arr1[i]+arr2[j]+carry)/10;
}

if(len1 > len2){
	while(i >=0){
		sum[k++]=(arr1[i] + carry)%10;
		carry=(arr1[i--]+carry)/10;
	}
	

}else if (len1<len2){
		while(j >=0){
		sum[k++]=(arr2[j] + carry)%10;
		carry=(arr2[j--]+carry)/10;

	}
}

if (carry>0){
		sum[k++]=carry;
	}

printf("SUM OF TWO NUMBERS \n");
for (k--; k >= 0; k--)
printf("%d", sum[k]);
}
////////////////////////////////////////////////////////////
int *subtract(char *p,char *q)


{   int subtract[strlen(p)+strlen(q)+100];
	int arr1[strlen(p)+1],arr2[strlen(q)+1];
	int i,j,k=0,borrow=0;

	int result[strlen(p)+strlen(q)+5];
	while(i>=0,j>=0){
		result[k]= (p-'0')-(q-'0')+borrow;
	if(result[k]< 0){
		result[k]+=10;                               //
		borrow=1;
	}else{
		borrow=0;
	}
k++;i--;j--;
	
}
	if(i >= 0){
while(i>=0){
	result[k]= (p-'0')-(q-'0')+borrow;
	if(result[k]<0){
		result[k]+=10;
		borrow=1;
	}else{
		borrow=0;
	}
k++;i--;	
}
	}


	if(j >= 0){
while(j>=0){
	result[k]= (p-'0')-(q-'0')+borrow;           
	if(result[k]<0){
		result[k]+=10;
		borrow=1;
	}else{
		borrow=0;
	}
k++;i--;	
}
	}
// 	k--;
// while(result[k]==0 && k!=0)
// k--;
// for(;k>=0;k--)
// printf("%d",result[k]);
int t;
t--;
while(result[t]==0 && t!=0)
t--;
for(;t>=0;t--)
printf("%d",result[t]);
}