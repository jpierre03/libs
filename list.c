#include <stdio.h>
#include <stdlib.h>
#include "list.h"
#include <unistd.h>

//****************************************************************************
//Initialisation de la liste.
//****************************************************************************
llist initList()
{
	element *elem = malloc(sizeof(*elem));

	if(elem == NULL)
	{
		exit(EXIT_FAILURE);
	}

	elem->value = 0;
	elem->nxt = NULL;

	printf("\n[initList] : la liste a ete initialisee.\n\n");
	return elem;
}
//****************************************************************************


//****************************************************************************
//Ajout d'un element en debut de liste.
//****************************************************************************
llist addHead(llist list, int value)
{
	//Cree un nouvel element
	element *newElement = malloc(sizeof(element));

	//Assignation de la valeur au nouvel element
	newElement->value = value;

	//Assignation de l'adresse de l'element suivant au nouvel element
	newElement->nxt = list;

	//Retour de la nouvelle liste, cad le pointeur sur le premier element
	return newElement;
}
//****************************************************************************


//****************************************************************************
//Ajout d'un element en fin de liste.
//****************************************************************************
llist addTail(llist list, int value)
{
	//Cree un nouvel element
	element *newElement = malloc(sizeof(element));

	//Assignation de la valeur au nouvel element
	newElement->value = value;

	//Assignation de l'adresse de l'element suivant au nouvel element
	newElement->nxt = NULL;

	//Si au depart la liste est vide, elle contient maintenant
	//seulement l'element qu'on vient de creer.
	//Dans ce cas il suffit de le renvoyer
	if(list == NULL)
	{
		return newElement;
	}
	//Sinon il faut parcourir la liste (à l'aide d'un pointeur temporaire)
	//et on fait pointer le dernier element de la liste
	//vers le nouvel element cree (qui devient donc le dernier)
	else
	{
		element *temp = list;

		while(temp->nxt != NULL)
		{
			temp = temp->nxt;
		}

		temp->nxt = newElement;

		return list;
	}
}
//****************************************************************************


//****************************************************************************
//Affichage d'une liste.
//****************************************************************************
void displayList(llist list)
{
	element *temp = list;

	//Si la liste est vide
	if(isEmpty(list))
	{
		printf("\n[displayList] : la liste est vide.\n\n");
	}
	else
	{
		printf("___________________\n");
		printf("\nCONTENU DE LA LISTE\n___________________\n\n");
		//Parcours de la liste
		while(temp->nxt != NULL)
		{
			printf("[%i] ", temp->value); //Affichage de la valeur
			temp = temp->nxt; //Passage à la case suivante
		}
		printf("[%i] ", temp->value); //Affichage de la derniere valeur
	}
	printf("\n\n");
}
//****************************************************************************


//****************************************************************************
//Test liste vide. Renvoie 1 si la liste est vide, 0 sinon.
//****************************************************************************
int isEmpty(llist list)
{
	if(list == NULL)
	{
		return 1;
	}
		
	return 0;
}
//****************************************************************************


//****************************************************************************
//Suppression 1er element.
//****************************************************************************
llist suppressHeader(llist list)
{
	//Si la liste est non vide
	if(list != NULL)
	{
		//Sauvegarde de l'adresse du second element
		element *newFirstElement = list->nxt; 
		//Suppression du premier element
		free(list);
		//Renvoi de la nouvelle liste, donc du nouveau premier element
		return newFirstElement;
	}

	//Si la liste est vide on retourne NULL
	return NULL;
}
//****************************************************************************


//****************************************************************************
//Suppression dernier element.
//****************************************************************************
llist suppressTail(llist list)
{
	//Si la liste est vide on retourne NULL
	if(list == NULL)
	{
		return NULL;
	}
		
	element *temp = list;
	element *previousTemp = list;

	//Si la liste ne contient qu'un element
	if(temp->nxt == NULL)
	{
		free(temp);
		return NULL;
	}

	//Parcours de la liste
	while(temp->nxt != NULL)
	{
		previousTemp = temp;
		temp = temp->nxt;
	}

	//A ce stade on est arrivé au dernier element
	//temp pointe sur le dernier element et previousTemp l'avant-dernier
	previousTemp->nxt = NULL;
	free(temp);

	return list;
}
//****************************************************************************


//****************************************************************************
//Recherche d'un element.
//****************************************************************************
llist findElement(llist list, int value)
{
	element *temp = list;

	if(!isEmpty(list))
	{
		while(temp != NULL)
		{
			if(temp->value == value)
			{
				return temp;
			}
			temp = temp->nxt;
		}
	}
	else
	{
		return NULL;
	}
}
//****************************************************************************


//****************************************************************************
//Comptage occurence d'un element.
//****************************************************************************
int countSomeElement(llist list, int value)
{
	element *temp = list;
	int count = 0;

	printf("\ntemp->value = %i\nvalue = %i\n\n", temp->value, value);
	if(isEmpty(list))
	{
		return 0;
	}
		
	while(temp->nxt != NULL)
	{
		if(temp->value == value)
		{
			printf("TROUVE!\ntemp->value = %i\nvalue = %i\n\n", temp->value, value);
			count++;
		}
		temp = temp->nxt;
	}
	//Derniere case
	if(temp->value == value)
	{
		count++;
	}

	return count;
}
//****************************************************************************


//****************************************************************************
//Recherche du ième element.
//****************************************************************************
llist findIthElement(llist list, int index)
{
	int i = 0;
	element *temp = list;

	for(i = 0; i < index && list != NULL; i++)
	{
		temp = temp->nxt;
	}

	//A ce stade on est sur la case d'indice désiré
	if(temp == NULL)
	{
		return NULL;
	}
	else
	{
		return temp;
	}
}
//****************************************************************************


//****************************************************************************
//Recuperer la valeur d'un element.
//****************************************************************************
int getElement(llist list, int index)
{
	int i = 0;
	element *temp = list;

	for(i = 0; i < index && list != NULL; i++)
	{
		temp = temp->nxt;
	}

	//A ce stade on est sur la case d'indice désiré
	if(temp == NULL)
	{
		return -1;
	}
	else
	{
		return temp->value;
	}
}
//****************************************************************************


//****************************************************************************
//Compter le nombre d'elements dans une liste.
//****************************************************************************
int countElementsInListRecursive(llist list)
{
	if(list == NULL)
	{
		return 0;
	}

	return countElementsInListRecursive(list->nxt) + 1;
}
//****************************************************************************


//****************************************************************************
//Compter le nombre d'elements dans une liste.
//****************************************************************************
int countElementsInListIterative(llist list)
{
	element *temp = list;
	int count = 0;

	if(list == NULL)
	{
		return 0;
	}

	while(temp -> nxt != NULL)
	{
		temp = temp->nxt;
		count++;
	}
	count++; //dernier element
	return count;
}
//****************************************************************************


//****************************************************************************
//Supprimer un element dans une liste.
//****************************************************************************
llist suppressElementsIterative(llist list, int value)
{
	element *temp = list;
	element *tempnxt;

	if(list == NULL)
	{
		return NULL;
	}

	while(temp->nxt != NULL)
	{
		printf("temp->value = %i\nvalue = %i\n\n", temp->value, value);
		if(temp->value == value)
		{
			printf("\n\nENTRE DANS if(temp->value == value)\n\n");
			tempnxt = temp->nxt;//Stockage element suivant
			free(temp);//Suppression element actuel
			temp = tempnxt;//Avance d'une case
			displayList(temp);
		}
		else
		{
			printf("\n\nDANS LE ELSE\n\n");
			temp = temp->nxt;
		}
	}
	return list;
}
//****************************************************************************


//****************************************************************************
//Supprimer un element dans une liste.
//****************************************************************************
llist suppressElementsRecursive(llist list, int value)
{
	if(list == NULL)
	{
		return NULL;
	}

	/* Si l'élément en cours de traitement doit être supprimé */
    if(list->value == value)
    {
        /* On le supprime en prenant soin de mémoriser 
        l'adresse de l'élément suivant */
        element* temp = list->nxt;
        free(list);
        /* L'élément ayant été supprimé, la liste commencera à l'élément suivant
        pointant sur une liste qui ne contient plus aucun élément ayant la valeur recherchée */
        temp = suppressElementsRecursive(temp, value);
        return temp;
    }
    else
    {
        /* Si l'élement en cours de traitement ne doit pas être supprimé,
        alors la liste finale commencera par cet élément et suivra une liste ne contenant
        plus d'élément ayant la valeur recherchée */
        list->nxt = suppressElementsRecursive(list->nxt, value);
        return list;
    }
}
//****************************************************************************


//****************************************************************************
//Effacer une liste de maniere iterative.
//****************************************************************************
llist deleteListIterative(llist list)
{
	element *temp = list, *tempnxt;

	while(temp != NULL)
	{
		tempnxt = temp->nxt;
		free(temp);
		temp = tempnxt;
	}
	printf("\n[deleteListIterative] : la liste a ete effacee.\n\n");
	return temp;
}
//****************************************************************************


//****************************************************************************
//Effacer une liste de maniere recursive.
//****************************************************************************
llist deleteListRecursive(llist list)
{
	if(list == NULL)
	{
		printf("\n[deleteListRecursive] : la liste a ete effacee.\n\n");
		return NULL;
	}
	else
	{
		element *temp = list->nxt; //decalage case suivante
		free(list);//suppression case actuelle
		//on recommence ce processus avec la case suivante 
		//qui devient la premiere case de la nouvelle liste
		return deleteListRecursive(temp);								
	}
}
//****************************************************************************
