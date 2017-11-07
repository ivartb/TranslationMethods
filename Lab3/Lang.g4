grammar Lang;

@header {
	import java.util.Set;
    import java.util.HashSet;
}

@members {
  Set<String> vars = new HashSet<>();
}

main 
	@init {
		System.out.println("#include \"stdio.h\"\n");
		System.out.println("int main() {");
	}
	
	@after {
		System.out.println("}");
	}
	: (line)*;
	
line  
	: assignment | input | output | ifclause;
	
ifclause
	@init {
		System.out.print("if (");
	}
	@after {
	System.out.println("}");
	}
	: '('cond')' iftrue 
	| '('cond')' iftrue iffalse;

iftrue : '?' '{'(line)+'}';

iffalse
	@init {
		System.out.println("} else {");
	}
	: ':' '{'(line)+'}';

cond returns [String res]
	@after {
		System.out.println($res + ") {");
	}
	: e1 = expr '>' e2 = expr {$res = $e1.res + " > " + $e2.res;}
	| e1 = expr '>''=' e2 = expr {$res = $e1.res + " >= " + $e2.res;}
	| e1 = expr '<' e2 = expr {$res = $e1.res + " < " + $e2.res;}
	| e1 = expr '<''=' e2 = expr {$res = $e1.res + " <= " + $e2.res;}
	| e1 = expr '=''=' e2 = expr {$res = $e1.res + " == " + $e2.res;}
	| e1 = expr '!''=' e2 = expr {$res = $e1.res + " != " + $e2.res;}; 
	
input returns [String out]
	@after {
		System.out.println("scanf(\"%d\", &" + $out + ");");
	}
	: VAR '<''<'
	{
		$out = $VAR.text;
		if (!vars.contains($VAR.text)) {
			vars.add($VAR.text);
			System.out.println("int " + $VAR.text + ";");
		}
	};
	
output returns [String out]
	@after {
		System.out.println("printf(\"%d\", " + $out + ");");
	}
	: VAR '>''>'
	{
		$out = $VAR.text;
		if (!vars.contains($VAR.text)) {
			vars.add($VAR.text);
			System.out.println("int " + $VAR.text + ";");
		}
	};
	
assignment returns [String out]
	@after {
		System.out.println($out + ";");
	}
	: VAR '=' expr 
	{
		$out = $VAR.text + " = " + $expr.res;
		if (!vars.contains($VAR.text)) {
			vars.add($VAR.text);
			System.out.println("int " + $VAR.text + ";");
		}
	};

expr returns [String res] : term exprS {$res = $term.res + $exprS.res;};
	
exprS returns [String res] 
		: {$res = "";}
		| '+' term exprS {$res = " + " + $term.res + $exprS.res;}
		| '-' term exprS {$res = " - " + $term.res + $exprS.res;};
		
term returns [String res] : factor termS
	{
		$res = $factor.res + $termS.res;
	};
	
termS returns [String res]  
		: {$res = "";}
		| '*' factor termS {$res = " * " + $factor.res + $termS.res;}
		| '/' factor termS {$res = " / " + $factor.res + $termS.res;};

factor returns [String res]
		: unit {$res = $unit.res;}
		| '-' unit {$res = "-" + $unit.res;};
		
unit returns [String res] 
		: NUMBER {$res = $NUMBER.text;}
		| VAR {$res = $VAR.text;}
		| '(' expr ')' {$res = "(" + $expr.res + ")";};

	
WHITESPACE : [ \t\r\n]+ -> skip;
NUMBER : [0-9]+;
VAR : [_a-zA-Z][_a-zA-Z0-9]*;