#!/usr/local/bin/python3

from flask import Flask, render_template, json, request
from flaskext.mysql import MySQL
from flask_cors import CORS, cross_origin

#import os
#import decimal

mysql = MySQL()
app = Flask(__name__)

# p = os.environ["PORT_DB"] = 'localhost'
# print(p)


# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'jay'
app.config['MYSQL_DATABASE_PASSWORD'] = 'jay'
app.config['MYSQL_DATABASE_DB'] = 'portiq'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


def decimal_default(obj):
    if isinstance(obj, decimal.Decimal):
        return float(obj)
    raise TypeError


# TODO: Add routes for shipping manifest updates and retrievals.
@app.route('/hello')
#@cross_origin()
def hello():
  return json.dumps({'data':'hello world!'})

@app.route('/getSchedule', methods=['POST'])
#@cross_origin()
def getSchedule():
  try:
    cursor = mysql.connect().cursor()
    CID = request.form.get('company', default=None, type=str)
    Date = request.form.get('date', default=None, type=str)
    port = request.form.get('port', default=None, type=str)
    cursor.execute('select * from portiq.data WHERE Unload_Port =\'{}\';'.format(port))
    data = cursor.fetchall()
    return json.dumps({'data': data})
  except Exception as e:
    return(str(e))



@app.route('/getPorts')
#@cross_origin()
def getPorts():
    return json.dumps({'data': [1,2,3]})


@app.route('/getData')
#@cross_origin()
def getExampleData():
  cursor = mysql.connect().cursor()
  cursor.execute("select * from data limit 1")
  data = cursor.fetchall()
  return json.dumps({'data': data}, default=decimal_default)


@app.route('/getPorts')
def getPorts():
    return json.dumps({'data': [1,2,3]})

if __name__ == "__main__":
    app.run(port=9003)








