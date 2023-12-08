const User = require('./User.js');
const Food = require('./Food.js');
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('../config/database.js');

const History = sequelize.define('History', {
  userId: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  foodId: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  date: {
    type: DataTypes.DATE,
    allowNull: false,
    defaultValue: Sequelize.fn('NOW'),
  },
  // Tambahkan atribut lain yang relevan
});


module.exports = History;
