# SOFE3980U-Lab5

In modern softwares, Machine Learning (ML) models are playing a crucial roles. Testing modern softwares should include testing the ML models. The designing of the ML model is out the Lab scope. Only ML testing will be covered. Different problems will be considered .For each problem type, crossponding metrcis is utilized.

## 1. Single-variable Continuous Regression Problem

The goal is to estimate the value of a continuous variable, $$y$$ ,using the values of input variables, $\mathbf{x}=\{x_{0},...,x_{n}\}$. This is achieved by learning the parameters, $\Theta$ of a function $f_{\Theta}(x)=f_{\Theta}(x_{0},...,x_{n})$ such that estimated value $\hat{y}=f_{\Theta}(x)$ should be much closer to the actual value , $y$. The actual value some times is called ground truth. To train the model, a datset, $\mathscr(D)$,pairs of inputs and output are needed evaluate the accuracy to the model, the input dataset which
