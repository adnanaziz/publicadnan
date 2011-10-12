/* 
 * Two good places to start reading: 
 *  - http://www.antlr.org/wiki/display/ANTLR3/Quick+Starter+on+Parser+Grammars+-+No+Past+Experience+Required
 *  - http://www.antlr.org/wiki/display/ANTLR3/Five+minute+introduction+to+ANTLR+3 
 *    (be warned, will take a lot more than 5 mins to read, despite the title)
 *
 * Example is from http://stackoverflow.com/questions/1931307/antlr-is-there-a-simple-example 
 * Example is based largely on Chapter 3, "The Definitive ANTLR Reference, Parr"
 */

/* 
 * Syntax rules
 *
 * : starts a rule definition and | seperates rule alternatives
 */

grammar BooleanExp;

eval returns [Node value]
    :    exp=quantList { $value = $exp.value; }
    ;


quantList returns [Node value]
    : 
        ForallQuantifier v=Variable q=quantList { 
        $value = new Node( NodeType.FORALL ); 
        $value.varName = $v.text;
        $value.children.add( $q.value );
        }
      |
        ExistsQuantifier v=Variable q=quantList { 
        $value = new Node( NodeType.EXISTS ); 
        $value.varName = $v.text;
        $value.children.add( $q.value );
        }
      | o1=orExp { $value = $o1.value; }
    ;

orExp returns [Node value]
    :    m1=andExp       { Node.log(" m1=andExp " ); 
                                $value =  $m1.value; 
                              }
         ( '+' m2=andExp { Node.log(" '+' m2=andExp"); 
                                Node tmp = new Node( NodeType.OR );
                                tmp.children.add( $value );
                                tmp.children.add( $m2.value );
                                $value = tmp; } 
         )* 
    ;

andExp returns [Node value]
    :    a1=invExp       {Node.log(" a1=invExp " ); $value =  $a1.value; }
         ( '*' a2=invExp {Node.log(" '*' a2=invExp " ); 
                          Node tmp = new Node( NodeType.AND ); 
                          tmp.children.add( value ); 
                          tmp.children.add( $a2.value ); 
                          $value = tmp; } 
         )* 
    ;

invExp returns [Node value]
    :    i1=atomExp       {Node.log(" i1=atomExp "); $value =  $i1.value;}
         | '!' i2=atomExp {Node.log(" '!1' "); 
                  $value = new Node( NodeType.NOT ); 
                  $value.children.add( $i2.value ); } 
    ;

atomExp returns [Node value]
    :    n=Variable {Node.log(" n=Variable "); 
                   $value = new Node( NodeType.VARIABLE ); 
                   $value.varName = $n.text;
                   $value.msg = $n.text;
                  }
    |    '(' exp=orExp ')' {Node.log(" '(' exp=orExp ')'"); $value = $exp.value;}
    ;


/* lexical rules */

ForallQuantifier
    : 'Forall' 
    ;

ExistsQuantifier
    : 'Exists' 
    ;

Variable
    :    ('a'..'z')+ 
    ;

/* 
 * From http://www.antlr.org/wiki/display/ANTLR3/Grammars
 * The $channel=HIDDEN; action places those tokens on a hidden channel. 
 * They are still sent to the parser, but the parser does not see them. 
 * Actions, however, can ask for the hidden channel tokens. If you want 
 * to literally throw out tokens then use action skip(); 
 * (see org.antlr.runtime.Lexer.skip()). 
 */
WS  
    :   (' ' | '\t' | '\r'| '\n') {$channel=HIDDEN;}
    ;

