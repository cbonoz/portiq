#!/usr/local/bin/python3



from flask import Flask, render_template, json, request
from flaskext.mysql import MySQL
#from flask_cors import CORS, cross_origin

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
@app.route('/hello', methods=['GET', 'POST'])
def hello():
  try:
    cursor = mysql.connect().cursor()
    cursor.execute("select * from portiq.data limit 1")
    hello = cursor.fetchall()
    return json.dumps({'data': hello})
  except Exception as e:
    return(str(e))  

@app.route('/signin', methods=['POST'])
def signin():
  cursor = mysql.connect().cursor()
  try:
    email = request.form.get('email', default=None, type=str)
    passwd = request.form.get('password', default=None, type=str)
    cursor.execute("Select idUser from portiq.Users WHERE email = \'{0}\' and password = \'{1}\';".format(email, passwd))
    UID = cursor.fetchone()
    return json.dumps({'UID': UID})
  except Exception as e:
    return(str(e))


@app.route('/getSchedule', methods=['POST'])
def getSchedule():
  cursor = mysql.connect().cursor()
  try:
    date = request.form.get('date')
    cursor.execute("select * from portiq.data WHERE Estimated_Vessel_Arrival LIKE \"{}\";".format(date))
    data = cursor.fetchall()
    return json.dumps({'data': data})
  except Exception as e:
    return(str(e))


@app.route('/getPorts', methods=['POST'])
#@cross_origin()
def getPorts():
  cursor = mysql.connect().cursor()
  try:
    port = request.form.get('port', default=None, type=str)
    cursor.execute("select Container, Estimated_Vessel_Arrival from portiq.data WHERE Unload_Port =\'{}\';".format(port))
    data = cursor.fetchall()
    return json.dumps({'data': data})
  except Exception as e:
    return(str(e))

@app.route('/getData')
#@cross_origin()
def getExampleData():
  cursor = mysql.connect().cursor()
  cursor.execute("select * from data limit 10")
  data = cursor.fetchall()
  return json.dumps({'data': data}, default=decimal_default)

@app.route('/setBOL', methods=['POST'])
def setBOL():
  try:
    cursor = mysql.connect().cursor()
    CID = request.form.get('company', default=None, type=str)
    BOL = request.form.get('BOL', default=None, type=str)
    cursor.execute('INSERT INTO portiq.BillOfLaden (CompanyID, Bill_of_Lading) VALUES (\'{}\',\'{}\');'.format(CID, BOL))
  except Exception as e:
    return(str(e))


if __name__ == "__main__":
    app.run(port=9003)








