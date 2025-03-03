# SOFE3980U-Lab5

In modern softwares, Machine Learning (ML) models are playing a crucial roles. Testing modern softwares should include testing the ML models. The designing of the ML model is out the Lab scope. Only ML testing will be covered. Different problems will be considered .For each problem type, crossponding metrcis is utilized.

## 1. Single-variable Continuous Regression Problem

The goal is to estimate the value of a continuous variable, $$y$$ ,using the values of input variables, $`\mathbf{x}=\{x_{0},...,x_{n}\}`$. This is achieved by learning the parameters, $\Theta$ of a function $\hat{y}=f_{\Theta}(x)=f_{\Theta}(x_{0},...,x_{n})$ such that estimated value $\hat{y}=f_{\Theta}(x)$ should be much closer to the actual value , $y$. The actual value some times is called ground truth. A datset, $\mathcal{D}$, consists of pairs of inputs and output, $`(x^{(i)},y^{(i)}) \forall i \in \{ 0,...,n \}`$, are needed To train the model where $n$ is the length of the dataset. The data set is divided into three non-overlapped sub sets: training, validation and testing. The training set is used to tune the model. While the validation set is used to evaluate the training process and early-stop the training process if needed. Finally, the test process is used to blinded evaluation of different models and hyperparameters. Many metrics can be used as 

$MSE=\frac{1}{n} \sum_{i=0}^{n}{\big(y^{(i)}-\hat{y}^{(i)}\big)^2}$      (Mean Square Error)

$MAE=\frac{1}{n} \sum_{i=0}^{n}{|y^{(i)}-\hat{y}^{(i)}|}$      (Mean Absolute Error)

$MARE=\frac{1}{n} \sum_{i=0}^{n}{\frac{|y^{(i)}-\hat{y}^{(i)}|}{|y^{(i)}|+\epsilon}} *100%$      (Mean Absolute Relative Error)

where $\epsilon$ is a very small number to avoid divide by zero.
