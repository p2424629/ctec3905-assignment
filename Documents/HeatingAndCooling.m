clc

% Declare a new FIS
%a = newfis('Cooling and Heating');

%{
newfis is a constructor which can be overloaded

You can change the following:
    ANDmethod = {min, prod}
    Ormethod = {max, probor}
    Implication = {min, prod}
    Aggregation = {max, sum, probor}
    Defuzzification = {centroid, bisector, mom, lom, som}
%}

%                                                   Sys Type,  AND, OR,  Impl, Agg    Defuzzification
a=newfis('Cooling and Heating', 'mamdani', 'min', 'max', 'min', 'max', 'centroid');

% Input 1: Day of Year:
%1 = Jan 1st, 2 = Jan 2nd, ..., 31 = Jan 31st, 32 = Feb 1st
a=addvar(a,'input','Day of Year',[0 365]);

% Input 1's membership functions
a = addmf(a, 'input', 1, 'Winter(1)', 'gaussmf', [25 0]);
a = addmf(a, 'input', 1, 'Spring', 'gaussmf', [25 91.25]);
a = addmf(a, 'input', 1, 'Summer', 'gaussmf', [25 182.5]);
a = addmf(a, 'input', 1, 'Autumn', 'gaussmf', [25 273.75]);
a = addmf(a, 'input', 1, 'Winter(2)', 'gaussmf', [25 365]);

% Input 2: Time of Day (mins)
a = addvar(a, 'input', 'Time of Day (mins)', [0 1440]);

% Input 2's membership functions
a = addmf(a, 'input', 2, 'Twilight(1)', 'trapmf', [0 0 60 240]); 
a = addmf(a, 'input', 2, 'Morning', 'trapmf', [120 300 660 780]); 
a = addmf(a, 'input', 2, 'Afternoon', 'trapmf', [660 780 1020 1140]); 
a = addmf(a, 'input', 2, 'Evening', 'trapmf', [1020 1140 1320 1440]);
a = addmf(a, 'input', 2, 'Twilight(2)', 'trapmf', [1200 1380 1440 1440]); 

% Input 3: Temp (C)
a = addvar(a, 'input', 'Temp (C)', [-20 40]);

% Input 3's membership functions
a = addmf(a, 'input', 3, 'V. Cold', 'trapmf', [-20 -20 0 5]);
a = addmf(a, 'input', 3, 'Cold', 'trimf', [0 5 10]);
a = addmf(a, 'input', 3, 'Moderate', 'trimf', [5 10 15]);
a = addmf(a, 'input', 3, 'Warm', 'trimf', [10 15 20]);
a = addmf(a, 'input', 3, 'V. Warm', 'trapmf', [15 20 40 40]);

% Output 1: Cooling (%)
a = addvar(a, 'output', 'Cooling', [-10 100]);

% Output 1's membership functions
a = addmf(a, 'output', 1, 'Off', 'trapmf', [-10 -10 0 0]);
a = addmf(a, 'output', 1, 'Low', 'trapmf', [0 0 35 50]);
a = addmf(a, 'output', 1, 'Moderate', 'trimf', [30 50 70]);
a = addmf(a, 'output', 1, 'High', 'trapmf', [50 70 100 100]);

% Output 2: Heating (%)
a = addvar(a, 'output', 'Heating', [-10 100]);

% Output 2's membership functions
a = addmf(a, 'output', 2, 'Off', 'trapmf', [-10 -10 0 0]);
a = addmf(a, 'output', 2, 'Low', 'trapmf', [0 0 35 50]);
a = addmf(a, 'output', 2, 'Moderate', 'trimf', [30 50 70]);
a = addmf(a, 'output', 2, 'High', 'trapmf', [50 70 100 100]);

% The rulebase 
rule1 = [0 0 1 1 4 1 1];
rule2 = [0 0 5 4 1 1 1];
rule3 = [0 0 3 1 2 1 1];

% Collect the rules in an array
ruleList = [rule1;rule2;rule3];

% Add the rules to the system
a = addrule(a, ruleList);

% Print the rules to the command window
showrule(a)

% Read in the Excel data file
filename = ('heatingandcooling.xls');
testData = xlsread(filename);

% The for loop processing the date and printing to the command window
% Also, it writes the outout values to the same heatingandcooling.xls file
for i=1:size(testData,1)
        eval = evalfis([testData(i, 1), testData(i, 2), testData(i, 3) ], a);
        fprintf('%d) In(1): %.2f, In(2) %.2f, In(3) %.2f => Out(1): %.2f Out(2): %.2f  \n\n',i,testData(i, 1),testData(i, 2),testData(i, 3), eval);  
        xlswrite('heatingandcooling.xls', eval, 1, sprintf('E%d',i+1));
end

ruleview(a)

figure(1)
subplot(3,1,1), plotmf(a, 'input', 1)
subplot(3,1,2), plotmf(a, 'input', 2)
subplot(3,1,3), plotmf(a, 'input', 3)

figure(2)
subplot(2,1,1), plotmf(a, 'output', 1)
subplot(2,1,2), plotmf(a, 'output', 2)





