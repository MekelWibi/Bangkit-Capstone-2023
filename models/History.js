// Import model Food
const Food = require('./Food.js');

// Import model User
const User = require('./User.js');

// Inisialisasi Sequelize
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('../config/database.js');

// Definisi model History
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