# 🚀 How to Run the Screenshot App - Simple Guide

## ✅ Prerequisites Check
You have everything ready:
- ✅ Java 21 (installed)
- ✅ Maven 3.9.9 (installed)

## 🎯 3 Easy Ways to Run This Project

### Option 1: Quick Demo (Recommended for First Time)
```bash
# This will show you what the app can do
./run-screenshot-app.sh demo
```

### Option 2: Using Maven Directly
```bash
# Step 1: Compile the project
mvn clean compile

# Step 2: Run the demo
mvn exec:java -Dexec.mainClass="com.screenshot.app.demo.ScreenshotDemo"
```

### Option 3: Run Tests
```bash
# Run all screenshot tests
mvn test
```

## 🖥️ What Each Option Does

### Demo Mode (`./run-screenshot-app.sh demo`)
- Opens Google homepage
- Takes 5 different types of screenshots:
  1. Basic page screenshot
  2. Full page screenshot 
  3. Element screenshot (Google logo)
  4. Search results screenshot
  5. Comparison screenshot
- Saves all screenshots to `screenshots/` folder
- Shows you exactly how the app works

### Test Mode (`mvn test`)
- Runs automated tests
- Takes screenshots during test execution
- Generates test reports in `reports/` folder

## 📁 Where to Find Results

After running, check these folders:
- `screenshots/` - All screenshot images
- `logs/` - Application logs
- `reports/` - Test reports (if you ran tests)

## 🚨 Common Issues & Solutions

### Issue: "Permission denied" on Linux/Mac
```bash
# Fix: Make the script executable
chmod +x run-screenshot-app.sh
```

### Issue: Chrome browser not found
```bash
# Install Chrome (Ubuntu/Debian)
wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
sudo apt update
sudo apt install google-chrome-stable
```

### Issue: Display issues on headless systems
```bash
# Use headless mode
./run-screenshot-app.sh test-headless
```

## 🎮 Try This Right Now

**Copy and paste this command to see it work:**

```bash
mvn clean compile exec:java -Dexec.mainClass="com.screenshot.app.demo.ScreenshotDemo"
```

This will:
1. Compile the project
2. Open a browser
3. Take several screenshots
4. Save them to the `screenshots/` folder
5. Show you exactly what happened in the console

## 🎯 Next Steps

1. **First**: Run the demo to see it working
2. **Then**: Look at the screenshots in the `screenshots/` folder
3. **Finally**: Explore the code in `src/main/java/` to understand how it works

## 📞 Need Help?

The project includes:
- **README.md** - Complete documentation
- **PROJECT_SUMMARY.md** - Technical overview
- **QUICK_START.md** - Alternative quick start guide

All the scripts and commands are designed to work out of the box!