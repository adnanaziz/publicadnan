#include <stdio.h>
#include <stdlib.h>

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
#define IN_LEN      (5366146*sizeof(double))
#elif defined(LZO_ARCH_I086) && !defined(LZO_HAVE_MM_HUGE_ARRAY)
#define IN_LEN      (5366146*sizeof(double))
#else
#define IN_LEN      (5366146*sizeof(double))
#endif
#define OUT_LEN     (IN_LEN + IN_LEN / 16 + 64 + 3)

static unsigned char __LZO_MMODEL in  [ IN_LEN ];
static unsigned char __LZO_MMODEL out [ OUT_LEN ];
*/


/* Work-memory needed for compression. Allocate memory in units
 * of 'lzo_align_t' (instead of 'char') to make sure it is properly aligned.
 */

#define HEAP_ALLOC(var,size) \
    lzo_align_t __LZO_MMODEL var [ ((size) + (sizeof(lzo_align_t) - 1)) / sizeof(lzo_align_t) ]

static HEAP_ALLOC(wrkmem, LZO1X_1_MEM_COMPRESS);

int main(int argc, char *argv[])
{
    int r;
    lzo_uint new_len;
    lzo_uint adnan_out_len;
    lzo_uint adnan_in_len;

    if (lzo_init() != LZO_E_OK)
    {
        printf("internal error - lzo_init() failed !!!\n");
        printf("(this usually indicates a compiler bug -  "
                  "try recompiling\nwithout optimizations,"
                  "and enable '-DLZO_DEBUG' for diagnostics)\n");
        return 3;
    }

    // 5366146 is the number of lines in q.txt, which
    // is the number of quotes
    adnan_out_len = adnan_in_len = 5366146*sizeof(double);
    static char adnan_in[5366146*sizeof(double)];
    static char adnan_out[5366146*sizeof(double)];
    char *tmp = adnan_in;
    FILE *fp = fopen("q.txt", "r");
    double p;
    while ( EOF != fscanf(fp, "%lf", &p ) ) {
      // printf("%lf\n", p );
      *((double *)tmp) = p;
      tmp += sizeof(double);
    }
    fclose(fp);
    int i;
    for ( i = 0 ; i < 5366146; i++ ) {
      // printf("wrote in %lf\n", ((double*)adnan_in)[i]);
    }


    // AA: commented out
    // r = lzo1x_1_compress(in,in_len,out,&out_len,wrkmem);
    printf("adnan_in_len = %d\n", adnan_in_len);
    r = lzo1x_1_compress(adnan_in,adnan_in_len,adnan_out,&adnan_out_len,wrkmem);
    if (r == LZO_E_OK)
        printf("compressed %lu bytes into %lu bytes\n",
            (unsigned long) adnan_in_len, (unsigned long) adnan_out_len);
    else
    {
        /* this should NEVER happen */
        printf("internal error - compression failed: %d\n", r);
        return 2;
    }
    /* check for an incompressible block */
    if (adnan_out_len >= adnan_in_len)
    {
        printf("This block contains incompressible data.\n");
        return 0;
    }


    new_len = adnan_in_len;
    // r = lzo1x_decompress(out,out_len,in,&new_len,NULL);
    r = lzo1x_decompress(adnan_out,adnan_out_len,adnan_in,&new_len,NULL);
    if (r == LZO_E_OK && new_len == adnan_in_len)
        printf("decompressed %lu bytes back into %lu bytes\n",
            (unsigned long) adnan_out_len, (unsigned long) adnan_in_len);
    else
    {
        /* this should NEVER happen */
        printf("internal error - decompression failed: %d\n", r);
        return 1;
    }

    printf("\nminiLZO simple compression test passed.\n");
    return 0;
}
