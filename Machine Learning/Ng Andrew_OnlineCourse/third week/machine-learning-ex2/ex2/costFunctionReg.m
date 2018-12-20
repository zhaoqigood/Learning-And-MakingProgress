function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples
n = length(theta);
% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta
s1 = 0;
s2 = 0;
for i = 1:m
    h = X(i,:)*theta;
    s1 = s1 -y(i)*log(sigmoid(h))-(1-y(i))*log(1-sigmoid(h))
endfor
for i = 2:n
  s2 = s2 + theta(i)*theta(i);
endfor
J = s1/m + lambda*s2/(2*m);
s3 = 0;
for i = 1:m
  s3 = s3 + (sigmoid(X(i,:)*theta)-y(i))*X(i,1) 
endfor
grad(1) = s3/m;
for i = 2:n
  s4 = 0;
  for j = 1:m
    s4 = s4 + (sigmoid(X(j,:)*theta)-y(j))*X(j,i)
  endfor
  grad(i) = s4/m + lambda*theta(i)/m;
endfor
% =============================================================

end
