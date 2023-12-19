const { Sequelize } = require('sequelize');

const DB_HOST = process.env.DB_HOST || "localhost";
const DB_USER = process.env.DB_USER || "root";
const DB_PASSWORD = process.env.DB_PASSWORD || "";
const DB_NAME = process.env.DB_NAME || "calofruit";

// Konfigurasi koneksi database
const sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASSWORD, {
  host: DB_HOST,
  dialect: 'mysql',
  logging: false, // Set true untuk log query SQL ke konsol
});

// Coba koneksi ke database
sequelize
  .authenticate()
  .then(() => {
    console.log('Koneksi ke database berhasil');
  })
  .catch((err) => {
    console.error('Gagal terkoneksi ke database:', err);
  });

module.exports = sequelize;
