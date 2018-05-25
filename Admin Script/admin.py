import pandas as pd
import numpy
import json
from firebase import firebase

firebase = firebase.FirebaseApplication('https://eattend-f20ad.firebaseio.com/',None)
result = json.dumps(firebase.get('/users', None))
result1 = json.loads(result)
result2 = pd.DataFrame(result1)
extra = pd.DataFrame()
extra = result2.as_matrix()
extra = pd.Series(extra[0]).to_dict()
print(result2)
print(type(result2))
count=0
for i in range(1,13):
    if str(i) in extra[0]['2018']:
        for key in extra[0]['2018'][str(i)]:
            if(key=='total'):
                break;
            x=key
        print(x) 
        percent=(float(extra[0]['2018'][str(i)]['total'])/float(x))*100
result2.insert(0,"Percentage",percent)
result3 = result2.to_csv('data.csv')
