#include <stdio.h>
#include <stdlib.h>
#include <snappy-c.h>

// 5366146 is the number of lines in q.txt, which
// is the number of quotes
#define N 5366146

#define LZO 0
#define SNAPPY 1

/*************************************************************************
// This program shows the basic usage of the LZO library.
// We will compress a block of data and decompress again.
//
// For more information, documentation, example programs and other support
// files (like Makefiles and build scripts) please download the full LZO
// package from
//    http://www.oberhumer.com/opensource/lzo/
**************************************************************************/

/* First let's include "minizo.h". */

#include "minilzo.h"


/* We want to compress the data block at 'in' with length 'IN_LEN' to
 * the block at 'out'. Because the input block may be incompressible,
 * we must provide a little more output space in case that compression
 * is not possible.
 */

/*
#if defined(__LZO_STRICT_16BIT)
#define IN_LEN      (N*sizeof(double))
#elif defined(LZO_ARCH_I086) && !defined(LZO_HAVE_MM_HUGE_ARRAY)
#define IN_LEN      (N*sizeof(double))
#else
#define IN_LEN      (N*sizeof(double))
#endif
#define OUT_LEN     (IN_LEN + IN_LEN / 16 + 64 + 3)

static unsigned char __LZO_MMODEL in  [ IN_LEN ];
static unsigned char __LZO_MMODEL out [ OUT_LEN ];
*/


/* Work-memory needed for compression. Allocate memory in units
 * of 'lzo_align_t' (instead of 'char') to make sure it is properly aligned.
 */

extern int profile(int, int);
extern int lzo(int, char *, lzo_uint, char *, lzo_align_t *);
extern int snappy(int, char *, lzo_uint, char *);
extern int init();

#define HEAP_ALLOC(var,size) \
    lzo_align_t __LZO_MMODEL var [ ((size) + (sizeof(lzo_align_t) - 1)) / sizeof(lzo_align_t) ]

static HEAP_ALLOC(wrkmem, LZO1X_1_MEM_COMPRESS);

int main(int argc, char *argv[]){
  int i;
  int doDecomp=0;
  init();
  for ( i = 0 ; i < 100; i++ ) {
    profile(doDecomp, LZO);
  }
}



int adnan_in_len = N*sizeof(double);
static char adnan_in[N*sizeof(double)];
static char adnan_out[2*N*sizeof(double)];

int init() {
   char *tmp = adnan_in;
   FILE *fp = fopen("q.txt", "r");
   if ( fp == NULL ) {
     // create dummy file
     fp = fopen("q.txt", "w");
     int j;
     for ( j = 0 ; j < N; j++ ) {
        fprintf(fp, "%d\n", rand() % 128 ); 
     }
     fclose(fp);
     fp = fopen("q.txt", "r");
   }
   double p;
   while ( EOF != fscanf(fp, "%lf", &p ) ) {
     // printf("%lf\n", p );
     *((double *)tmp) = p;
     tmp += sizeof(double);
   }
   fclose(fp);
   printf("input read in\n");
}



int profile(int doDecomp, int type) {
   if ( type ==  LZO ) {
     lzo( doDecomp, adnan_in, adnan_in_len, adnan_out, wrkmem );
   } else {
     snappy( doDecomp, adnan_in, adnan_in_len, adnan_out );
   }
}


int snappy(int dodecomp, char *adnan_in, lzo_uint adnan_in_len, char *adnan_out ) {
   long int tmp;
   long int adnan_out_len = 2*adnan_in_len;
   int r = snappy_compress(adnan_in, adnan_in_len, adnan_out, &adnan_out_len);
   if ( !dodecomp ) 
     return 0;

   int s = snappy_uncompress(adnan_out, adnan_out_len, adnan_in, &tmp);
   printf("snappy before = %d, after = %d\n", adnan_in_len, adnan_out_len);
   if ( r != SNAPPY_OK  )
      printf("Problem with snappy - r! %d\n", r);
   if ( s != SNAPPY_OK ) 
      printf("Problem with snappy - s! %d\n", s);
   if ( tmp != adnan_in_len ) 
      printf("Problem with snappy - invert! %d %d\n", tmp, adnan_in_len);
}


int lzo(int dodecomp, char *adnan_in, lzo_uint adnan_in_len, char *adnan_out, lzo_align_t  *wrkmem ) {

   if (lzo_init() != LZO_E_OK) {
       printf("internal error - lzo_init() failed !!!\n");
       printf("(this usually indicates a compiler bug -  "
                 "try recompiling\nwithout optimizations,"
                 "and enable '-DLZO_DEBUG' for diagnostics)\n");
       return 3;
   }

   // AA: commented out
   // r = lzo1x_1_compress(in,in_len,out,&out_len,wrkmem);
   int r;
   lzo_uint new_len;
   lzo_uint adnan_out_len;

   printf("adnan_in_len = %d\n", adnan_in_len);
   r = lzo1x_1_compress(adnan_in,adnan_in_len,adnan_out,&adnan_out_len, wrkmem);
   if (r == LZO_E_OK)
       printf("compressed %lu bytes into %lu bytes\n",
           (unsigned long) adnan_in_len, (unsigned long) adnan_out_len);
   else {
       /* this should NEVER happen */
       printf("internal error - compression failed: %d\n", r);
       return 2;
   }
   /* check for an incompressible block */
   if (adnan_out_len >= adnan_in_len) {
       printf("This block contains incompressible data.\n");
       return 0;
   }

   new_len = adnan_in_len;
   if ( !dodecomp ) {
     return 0;
   }

   // r = lzo1x_decompress(out,out_len,in,&new_len,NULL);
   r = lzo1x_decompress(adnan_out,adnan_out_len,adnan_in,&new_len,NULL);
   if (r == LZO_E_OK && new_len == adnan_in_len)
       printf("decompressed %lu bytes back into %lu bytes\n",
           (unsigned long) adnan_out_len, (unsigned long) adnan_in_len);
   else {
       /* this should NEVER happen */
       printf("internal error - decompression failed: %d\n", r);
       return 1;
   }

   printf("\nminiLZO simple compression test passed.\n");
   return 0;
}
