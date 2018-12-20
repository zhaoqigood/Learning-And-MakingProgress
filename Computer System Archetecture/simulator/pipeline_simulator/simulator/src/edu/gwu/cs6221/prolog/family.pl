/*uncle(X, Y) :– male(X), sibling(X, Z), parent(Z, Y).*/
/*uncle(X, Y) :– male(X), spouse(X, W), sibling(W, Z), parent(Z, Y).*/

uncle(X,Y) :-
    parent(Z,Y), brother(X,Z). 
  
  aunt(X,Y) :-
    parent(Z,Y), sister(X,Z). 
  
  sibling(X, Y) :-
        parent(Z, X),
        parent(Z, Y),
        X \= Y.
  
  sister(X, Y) :-
        sibling(X, Y),
        female(X).
  
  brother(X, Y) :-
        sibling(X, Y),
        male(X).
  
  parent(Z,Y) :- father(Z,Y).
  parent(Z,Y) :- mother(Z,Y).
  
  grandparent(C,D) :- parent(C,E), parent(E,D).
  
  /*
  aunt(X, Y) :– female(X), sibling(X, Z), parent(Z, Y).
  aunt(X, Y) :– female(X), spouse(X, W), sibling(W, Z), parent(Z, Y).
  */
  male(kebo).
  male(ruitong).
  male(banghui).
  male(ruiying).
  
  female(ruiying).
  female(xin).
  female(yunfang).
  female(yabo).
  parent(kebo, ruitong).
  parent(kebo, ruiying).
  parent(xin, ruitong).
  parent(xin, ruiying).




  parent(mary, sue).

  
  sibling(bob,bill).
  sibling(sue,bill).
  sibling(nancy,jeff).
  sibling(nancy,ron).
  sibling(jell,ron).