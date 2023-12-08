const express = require('express');
const cookieParser = require('cookie-parser');
const authRoutes = require('./routes/auth');
const foodRoutes = require('./routes/food');
const historyRoutes = require('./routes/history');

const Food = require('./models/Food.js');
const History = require('./models/History.js');
const User = require('./models/User.js')

const app = express();

try {
    console.log('db ok');
    User.sync();
} catch (error) {
    console.error(error);
}

app.use(express.json());
app.use(cookieParser());

// Gunakan rute
app.use('/auth', authRoutes);
app.use('/food', foodRoutes);
app.use('/history', historyRoutes);

const PORT = process.env.PORT || 4000;
app.listen(PORT, () => {
  console.log(`Server berjalan di http://localhost:${PORT}`);
});
