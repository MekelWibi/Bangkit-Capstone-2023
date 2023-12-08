// Import model History
const History = require('./History.js');

// Inisialisasi Sequelize
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('../config/database.js');

// Definisi model Food
const Food = sequelize.define('Food', {
  name: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  calories: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
});

// Definisi relasi dengan model History

module.exports = Food;
