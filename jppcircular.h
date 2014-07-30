
/**
 * http://en.wikipedia.org/wiki/Circular_buffer#Always_Keep_One_Slot_Open
 */

/* Circular buffer object */
typedef struct {
	int         size;   /* maximum number of elements           */
	int         start;  /* index of oldest element              */
	int         count;  /* number of stored elements            */
	ElemType   *elems;  /* vector of elements                   */
} CircularBuffer;

void cbFree(CircularBuffer *cb) {
	free(cb->elems); /* OK if null */
}

void cbInit(CircularBuffer *cb, const int size) {
    cb->size  = size;
    cb->start = 0;
    cb->count = 0;
    cb->elems = (ElemType *)calloc(cb->size, sizeof(ElemType));
}

int cbIsFull(const CircularBuffer *cb) {
    return cb->count == cb->size;
}

int cbIsEmpty(CircularBuffer *cb) {
    return cb->count == 0;
}

void cbWrite(CircularBuffer *cb, const ElemType *elem) {
    int end = (cb->start + cb->count) % cb->size;
    cb->elems[end] = *elem;
    if (cb->count == cb->size){
        cb->start = (cb->start + 1) % cb->size; /* full, overwrite */
    } else {
        ++ cb->count;
    }
}

void cbRead(CircularBuffer *cb, ElemType *elem) {
    *elem = cb->elems[cb->start];
    cb->start = (cb->start + 1) % cb->size;
    -- cb->count;
}
