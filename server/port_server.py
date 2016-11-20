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
    cursor.execute("Select idUser from portiq.Users WHERE email = \'{0}\' and password = \'{1}\'".format(email, passwd))
    UID = cursor.fetchone()
    return json.dumps({'UID': UID})
  except Exception as e:
    return(str(e))

@app.route('/getSchedule', methods=['POST'])
#@cross_origin()
def getSchedule():
  try:
    cursor = mysql.connect().cursor()
    CID = request.form.get('company', default=None, type=str)
    Date = request.form.get('date', default=None, type=str)
    port = request.form.get('port', default=None, type=str)

    if Date != None:
      cursor.execute("select Unload_Port, Container, Estimated_Vessel_Arrival from portiq.data WHERE Estimated_Vessel_Arrival =\'{}\';".format(date))
      data = cursor.fetchall()

    if port != None:
      cursor.execute("select Container, Estimated_Vessel_Arrival, Estimated_Ship_Departure from portiq.data WHERE Unload_Port Like\'{}\';".format(port))
      data = cursor.fetchall()
    return json.dumps({'data': data})
  except Exception as e:
    return(str(e))



@app.route('/getPorts', methods=['POST'])
#@cross_origin()
def getPorts():
  port = request.form.get('port', default=None, type=str)
  return json.dumps({'data': port})


@app.route('/getData')
#@cross_origin()
def getExampleData():
  cursor = mysql.connect().cursor()
  cursor.execute("select * from data limit 1")
  data = cursor.fetchall()
  return json.dumps({'data': data}, default=decimal_default)



if __name__ == "__main__":
    app.run(port=9003)








