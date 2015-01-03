#ifndef LIST_H
#define LIST_H

typedef struct element element;
struct element
{
	int value;
	struct element *nxt;
};

typedef element *llist;

llist initList();
llist addHead(llist list, int value);
llist addTail(llist list, int value);
void displayList(llist list);
int isEmpty(llist list);
llist suppressHeader(llist list);
llist suppressTail(llist list);
llist findElement(llist list, int value);
int countSomeElement(llist list, int value);
llist findIthElement(llist list, int index);
int getElement(llist list, int index);
int countElementsInListRecursive(llist list);
int countElementsInListIterative(llist list);
llist suppressElementsIterative(llist list, int value); // Ne fonctionne pas 
llist suppressElementsRecursive(llist list, int value);
llist  deleteListIterative(llist list);
llist  deleteListRecursive(llist list);

#endif