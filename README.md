# SOFE3980U-Lab5

Machine Learning (ML) models play a crucial role in modern software. Testing modern software should include testing the ML models. The design of the ML model is outside the lab's scope. Only ML testing will be covered. Different problems will be considered. For each problem type, corresponding metrics are utilized.

## 1. Single-variable Continuous Regression Problem

The goal is to estimate the value of a continuous variable, $$𝑦$$, using the values of input variables, $`\mathbf{x}=\{x_{0},...,x_{n}\}`$. This is achieved by learning the parameters, $\Theta$ of a function $\hat{y}=f_{\Theta}(x)=f_{\Theta}(x_{0},...,x_{n})$ such that estimated value $\hat{y}=f_{\Theta}(x)$ should be much closer to the actual value, $y$. The actual value is sometimes called ground truth. A dataset, $`\mathcal{D}`$, consists of pairs of inputs and output, $`(x^{(i)},y^{(i)}) \forall i \in \{ 0,...,n \}`$, are needed To train the model where $𝑛$ is the length of the dataset. The data set is divided into three non-overlapped sub-sets: training, validation, and testing. The training set is used to tune the model. The validation set is used to evaluate the training process and the training process early if needed. Finally, the test process is used for blind evaluation of different models and hyperparameters. Many metrics can be used as

$MSE=\frac{1}{n} \sum_{i=0}^{n}{\big(y^{(i)}-\hat{y}^{(i)}\big)^2}$      (Mean Square Error)

$`MAE=\frac{1}{n} \sum_{i=0}^{n}{\big|y^{(i)}-\hat{y}^{(i)}\big|}`$      (Mean Absolute Error)

$`MARE=\frac{1}{n} \sum_{i=0}^{n}{\frac{|y^{(i)}-\hat{y}^{(i)}|}{|y^{(i)}|+\epsilon}} *100\%`$      (Mean Absolute Relative Error)

where $\epsilon$ is a very small number to avoid dividing by zero.

## 2. Single-variable Binary Regression Problem
It's similar to the single-variable continuous regression problem except that the output variable has two values: either zero or one,  $`y \in \{0,1\}`$. The estimated variable has a continuous value between 0 and 1, $`\hat{y}=f_{\Theta}(\mathbf{x}) \in [0,1]`$. By applying a threshold its value can converted into binary value 

$`\hat{y}_{\text{binary}}=\begin{cases}
      1 & \text{if $\hat{y} >=$ threshold}\\
      0 & \text{if $\hat{y} <$ threshold}\\
    \end{cases}`$
    
The default value of the threshold is 0.5. a binary cross-entropy (BCE) is a metrics to evaluate the model.

$`BCE=\frac{1}{n} \sum_{i=0}^{n}{y^{(i)} log(\hat{y}^{(i)})+(1-y^{(i)}) log(1-\hat{y}^{(i)})}=\frac{1}{n} \sum_{i=0}^{n}{\begin{cases}
      log(\hat{y}^{(i)}) & \text{if $y^{(i)}=1$}\\
      log(1-\hat{y}^{(i)}) & \text{if $y^{(i)}=0$}\\
    \end{cases}}`$

Another metrics is confusion matrix which is summarized by the following figure.

$`Accuracy=\frac{TP+TN}{TP+TN+FN+FP}`$

$`Precision=\frac{TP}{TP+FP}`$

$`Recall=\frac{TP}{TP+FN}`$

$`F1_{\text{score}}=2 \frac{Precision*Recall}{Precision+Recall}`$

