
:-use_module(library(pairs)).
read_from_file(File) :-
    open(File,read,Stream),
    read_string(Stream,_,String1),
	string_lower(String1,LowerCase),
	string_chars(LowerCase,Chars),
	process_chars(Chars,st{},ST),
	english_word(List),
	                                           % NewChar store the value of a Char which is the most

	                                           % String1 represent the input String. 
	                                           % ST store the characters and the show times of these chatacters
    process_list(Chars,[],NewList,[],NewList1),% Chars store the list representation of the input string
	strings_to_atoms(NewList,[],AtomList),     
	strings_to_atoms(NewList1,[],AtomList1),   % NewList store the words,NewList1 store the three-word
	write("3"),nl,
	process_atoms(AtomList,st{},NewST),        % AtomList store the atom list of the words,AtomList1 store the atom list of three-words
	write("4"),nl,
	process_atoms(AtomList1,a{},NewST1),      
	write("5"),nl,
	sort(0,<,NewList,Sorted),                  % NewST store the word-times pair,NewST1 store the three-word-time pair
	sort(0,<,NewList1,Sorted1),
	dic_word(Sorted,"",NewString,NewST,1),     % NewString store the dictionary.
	close(Stream),                             % Sorted store the sorted word list
	write_on_file("dictionary.txt",NewString), % Sorted1 store the sorted three-word list
	st_get(NewST1,NewChar1,Value),
    get_pairs_list(List,ST,[],CharPair1),
	get_pairs_list(Sorted,NewST,[],WordPair1),
	get_pairs_list(Sorted1,NewST1,[],MultiWordPair1),
	sort(1,>=,CharPair1,CharPair2),
	sort(1,>=,WordPair1,WordPair2),
	sort(1,>=,MultiWordPair1,MultiWordPair2),
    get_string_pairs(CharPair2,"",CharSortList),
	get_string_pairs(WordPair2,"",WordSortList),
	get_string_pairs(MultiWordPair2,"",MultiWordSortList),
    write_on_file("Char_Sorted.txt",CharSortList),
	write_on_file("Word_Sorted.txt",WordSortList),
	write_on_file("MultiWord_Sorted.txt",MultiWordSortList).
write_on_file(File,Text):-
    open(File,write,Stream),
	write(Stream,Text),nl,
	close(Stream).
process_chars([],OldST,NewST):-
    NewST = OldST,
	!.
process_chars([X|List],OldST,NewST):-
    update_ST(X,OldST,ST),
    process_chars(List,ST,NewST).

update_ST(Key,OldST,NewST):-
    char_type(Key,alpha),
   	st_get(OldST,Key,Value),
	Value1 is Value+1,
    st_put(Key,OldST,Value1,NewST),!;
	NewST = OldST,!.

st_get(Dict,Key,Value):-
    get_dict(Key,Dict,Value),
	!;
	Value = 0,!.
st_put(Key,Dict1,Value,Dict2):-
    put_dict(Key,Dict1,Value,Dict2).
equal(X,Y):-
    X = Y.
dict_equal(X,Y):-
    X = Y.
max_number_char([], ST, OldChar,NewChar):-
    equal(NewChar,OldChar),
	!.
max_number_char([X|List],ST,OldChar,NewChar):-
    atom_string(Y,X),
    st_get(ST,Y,Value1),
    st_get(ST,OldChar,Value2),
	(Value1 > Value2 -> 
	equal(Char,Y),!;
	equal(Char,OldChar),!),
	max_number_char(List,ST,Char,NewChar).
english_word(List):-
    List = [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z].
process_list([],OldList,NewList,OldList1,NewList1):-
    NewList = OldList,
	NewList1 = OldList1,
	!.
process_list([X|List],OldList,NewList,OldList1,NewList1):-
    char_type(X,alpha),
	create_word(List,X,NewString,RemainList),
	create_multi_word(List,X,NewString1,0,NewN,X,EndChar),
    (
	 char_type(EndChar,alpha),
	 NewN = 2,
	 equal(OldList11,[NewString1|OldList1]),!;
	 equal(OldList11,OldList1),!),
	process_list(RemainList,[NewString|OldList],NewList,OldList11,NewList1),!;
	process_list(List,OldList,NewList,OldList1,NewList1),!.
    
create_word([X|List],OldString,NewString,RemainList):-
    char_type(X,alpha),
	string_concat(OldString,X,String),
	create_word(List,String,NewString,RemainList),!;
	NewString = OldString,
	RemainList = List,!.
    
create_multi_word([X|List],OldString,NewString,OldN,NewN,Char,EndChar):-
    ((char_type(X,alpha),
	N1 is OldN),!;
    (	
    char_type(X,white),
	N1 is OldN+1,
	N1<3),!),
	string_concat(OldString,X,String),
	create_multi_word(List,String,NewString,N1,NewN,X,EndChar),!;
	NewString = OldString,
	NewN = OldN,
	EndChar = Char,!.
process_atoms([],OldST,NewST):-
    NewST = OldST,
	!.
process_atoms([X|List],OldST,NewST):-
    update_ST1(X,OldST,ST),
    process_atoms(List,ST,NewST).

update_ST1(Key,OldST,NewST):-
   	st_get(OldST,Key,Value),
	Value1 is Value+1,
    st_put(Key,OldST,Value1,NewST).

strings_to_atoms([],OldList,AtomList):-
    AtomList = OldList,
	!.
strings_to_atoms([X|List],OldList,AtomList):-
    atom_string(Y,X),
	strings_to_atoms(List,[Y|OldList],AtomList).

dic_word([],OldString,NewString,ST,Index):-
    NewString = OldString,
	!.
dic_word([X|List],OldString,NewString,ST,Index):-
    number_string(Index,String1),
    string_concat(String1,". ",String2),
	string_concat(String2, X, String3),
	string_concat(String3,"---------",String4),
	atom_string(Atom,X),
	st_get(ST,Atom,Value),
	number_string(Value,String5),
	string_concat(String4,String5,String6),
	string_concat(String6,"\n",String7),
	string_concat(OldString,String7,String8),
	Index1 is Index+1,
	dic_word(List,String8,NewString,ST,Index1).

get_pairs_list([],ST,OldList,NewList):-
    NewList = OldList,
	!.
get_pairs_list([X|List],ST,OldList,NewList):-
    atom_string(Y,X),
    st_get(ST,Y,Value),
	get_pairs_list(List,ST,[(Value,Y)|OldList],NewList).


get_string_pairs([],OldString,NewString):-
    NewString = OldString,
	!.
get_string_pairs([(Value,Key)|List],OldString,NewString):-
    atom_string(Value,String1),
	atom_string(Key, String2),
	string_concat(String2,"--------",String3),
    string_concat(String3,String1,String4),
	string_concat(String4,"\n",String5),
	string_concat(OldString,String5,String6),
    get_string_pairs(List,String6,NewString).