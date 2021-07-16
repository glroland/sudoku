function AppConfig() {
    this.DEFAULT_SUDOKU_SERVICE_URL = 'http://localhost:8080';
    this.DEFAULT_SUDOKU_OCR_SERVICE_URL = 'http://localhost:5000';
}

AppConfig.prototype.isValid = function(input) {
    if (input === undefined)
        return false;
    if (input === 'undefined')
        return false;

    return true;
}

AppConfig.prototype.getConfig = function() {
    let configFile;
    try 
    {
        configFile = require('/tmp/config.json');
    } 
    catch (err) 
    {
        configFile = require("./config.json");
    }
    return configFile;
}

// Sudoku Service Endpoint URL
AppConfig.prototype.getSudokuServiceUrl = function() {
    var result = this.DEFAULT_SUDOKU_SERVICE_URL;
    if (this.isValid(process.env.REACT_APP_SUDOKU_URL_SVC))
    {
        result = process.env.REACT_APP_SUDOKU_URL_SVC;
        console.log("getSudokuServiceUrl using environment variable - " + result);
    }
    else if(this.isValid(this.getConfig().REACT_APP_SUDOKU_URL_SVC)) {
        result = this.getConfig().REACT_APP_SUDOKU_URL_SVC;
        console.log("getSudokuServiceUrl using config file value - " + result);
    }
    else
    {
        console.log("getSudokuServiceUrl using default value - " + result);
    }
    return result;
}

// Sudoku OCR Service Endpoint URL
AppConfig.prototype.getSudokuOcrServiceUrl = function() {
    var result = this.DEFAULT_SUDOKU_OCR_SERVICE_URL;
    if (this.isValid(process.env.REACT_APP_SUDOKU_URL_OCRSVC))
    {
        result = process.env.REACT_APP_SUDOKU_URL_OCRSVC;
        console.log("getSudokuOcrServiceUrl using environment variable - " + result);
    }
    else if(this.isValid(this.getConfig().REACT_APP_SUDOKU_URL_OCRSVC)) {
        result = this.getConfig().REACT_APP_SUDOKU_URL_OCRSVC;
        console.log("getSudokuOcrServiceUrl using config file value - " + result);
    }
    else
    {
        console.log("getSudokuOcrServiceUrl using default value - " + result);
    }
    return result;
}

module.exports = AppConfig;
