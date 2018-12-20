function [J, grad] = costFunction(theta, X, y)
%COSTFUNCTION Compute cost and gradient for logistic regression
%   J = COSTFUNCTION(theta, X, y) computes the cost of using theta as the
%   parameter for logistic regression and the gradient of the cost
%   w.r.t. to the parameters.

% Initialize some useful values
m = length(y); % number of training examples
n = length(theta);
% You need to return the following variables correctly 
J = 0;
s = 0;
grad = zeros(size(theta));
for i = 1:m
    h = X(i,:)*theta;
    s = s + -y(i)*log(sigmoid(h))-(1-y(i))*log(1-sigmoid(h));
endfor
J = s/m;
for i = 1:n
  s1 = 0;
  for j = 1:m
    h1 = X(j,:)*theta;
    s1 = s1+ (sigmoid(h1)-y(j))*X(j,i);
  endfor
  grad(i) = s1/m;
endfor
% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta
%
% Note: grad should have the same dimensions as theta
%








% =============================================================

end
