// create an API to save and load file with DedupManager
// and compression

use std::collections::hash_map::DefaultHasher;
use std::collections::HashMap;
use std::fs::{File, OpenOptions};
use std::hash::{Hash, Hasher};
use std::io::{Read, Write};
use std::path::Path;
use std::fs;
// define constant



pub struct DedupManager {
    pub CHUNK_SIZE: usize,
    pub DATABASE_PATH: String,
    pub STATE_PATH: String,
    pub MAP1_PATH: String,
    pub MAP2_PATH: String
}
//get hash of an vector (input type is Vec<u8>)
fn get_hash(input: &Vec<u8>) -> u64{
    // let mut hasher = Sha256::new();
    let mut hasher = DefaultHasher::new();
    // hasher.update(input);
    // hasher.finalize() 
    input.hash(&mut hasher);
    let hash = hasher.finish();
    hash
}


// Check if the map exists and if it does load it
fn checkifexist(file_path: &str)
// return hashmap of string to vector of strings
-> HashMap<String, Vec<String>> {
    let path = Path::new(file_path);
    if !path.exists() {
        let mut file = File::create(path).unwrap();
    }
    let mut file = File::open(path).unwrap();
    let mut contents = String::new();
    file.read_to_string(&mut contents).unwrap();
    let mut map: HashMap<String, Vec<String>> = HashMap::new();
    for line in contents.lines() {
        // line is comma seperated with first value as key and rest as values
        let mut line_iter = line.split(",");
        let key = line_iter.next().unwrap();
        let mut values: Vec<String> = line_iter.map(|x| x.to_string()).collect();
        // pop last value as it is empty
        if values[values.len() - 1] == "" {
            values.pop();
        }
        map.insert(key.to_string(), values);

    }
    map
}


impl DedupManager {

    // reads the state of last chunk index and returns
    fn get_last_chunk_index(&self) -> u64 {
        // if state.txt does not exist create it
        let path = Path::new(&self.STATE_PATH);
        if !path.exists() {
            let mut file = File::create(&self.STATE_PATH[..]).unwrap();
            file.write_all(b"0").unwrap();
            return 0;
        }
        // read state.txt
        let mut file = File::open(&self.STATE_PATH[..]).unwrap();
        let mut contents = String::new();
        file.read_to_string(&mut contents).unwrap();
        let last_chunk_index = contents.parse::<u64>().unwrap();
        last_chunk_index + 1
        // File::open(STATE_PATH).unwrap().read_to_string(&mut String::new()).unwrap()
    }

    // setups the directories if they do not exist
    fn setup(& self){
        // if state.txt does not exist create it and set it to 0
        let path = Path::new(&self.DATABASE_PATH);
        if !path.exists() {
            std::fs::create_dir(path).unwrap();
        }

        let path = Path::new(&self.STATE_PATH);
        if !path.exists() {
            let mut file = File::create(&self.STATE_PATH[..]).unwrap();
            file.write_all(b"0").unwrap();
        }
        // if map1.txt does not exist create it
        let path = Path::new(&self.MAP1_PATH);
        if !path.exists() {
            let mut file = File::create(path).unwrap();
        }
        // if map2.txt does not exist create it
        let path = Path::new(&self.MAP2_PATH);
        if !path.exists() {
            let mut file = File::create(path).unwrap();
        }
        // if chunks directory does not exist create it
        let path = Path::new(&self.DATABASE_PATH).join("chunks");
        if !path.exists() {
            std::fs::create_dir(path).unwrap();
        }
        
    }

    // reset button. Deletes database 
    fn clear(&self){
        fs::remove_file(&self.MAP1_PATH);
        fs::remove_file(&self.MAP2_PATH);
        fs::remove_file(&self.STATE_PATH);
        // fs::remove_dir_all(DATABASE_PATH+"/chunks");
        fs::remove_dir(&self.DATABASE_PATH);
    }

    // given a byte file get the chunks and hashes
    fn get_hashes
    // return vector of tupe {chunk, hash} from vector of bytes
    (&self, input: &Vec<u8>) -> 
    Vec<(Vec<u8>, u64)> {
        let mut hashes: Vec<(Vec<u8>, u64)> = Vec::new();
        let mut start = 0;
        let mut end = self.CHUNK_SIZE;
        while start < input.len() {
            if end > input.len() {
                end = input.len();
            }
            let chunk = input[start..end].to_vec();
            let hash = get_hash(&chunk);
            hashes.push((chunk, hash));
            start = end;
            end += self.CHUNK_SIZE;
        }
        
        hashes
    }

    // saves the entire file to the database
    pub fn save_file(&self, path: &str) {
        self.setup();
        // let mut file = File::open(path).unwrap();
        // read file from path
        let mut file = File::open(path).unwrap();
        let mut buffer = Vec::new();
        file.read_to_end(&mut buffer).unwrap();

        // get hashes of file
        let hashes = self.get_hashes(&buffer);

        let mut hashtochunk = checkifexist(&self.MAP1_PATH);
        let mut filetohashes = checkifexist(&self.MAP2_PATH);

        // append hashes to csv file with as filename, hasharray
        // if file exists append to it
        // else create new file
        let mut file = OpenOptions::new()
            .write(true)
            .append(true)
            .create(true)
            .open(&self.MAP2_PATH)
            .unwrap();
        file.write_all(path.as_bytes()).unwrap();
        file.write_all(b",").unwrap();
        for hash in &hashes {
            file.write_all(hash.1.to_string().as_bytes()).unwrap();
            // file.write_all(hash.to_string().as_bytes()).unwrap();
            file.write_all(b",").unwrap();
        }
        file.write_all(b"\n").unwrap();




        //save chunks to disk
        let mut i = 0;
        // recover last chunk index
        i = self.get_last_chunk_index();

        // save only chunks which are not in disk

        for chunk_hash in &hashes{
            if !hashtochunk.contains_key(&chunk_hash.1.to_string()) {
                let mut file = File::create(format!("{}chunks/{}.chunk",self.DATABASE_PATH, i)).unwrap();
                file.write_all(&chunk_hash.0).unwrap();
                hashtochunk.insert(chunk_hash.1.to_string(), vec![format!("{}.chunk", i)]);
                // write to map1.txt
                let mut file = OpenOptions::new()
                    .write(true)
                    .append(true)
                    .create(true)
                    .open(&self.MAP1_PATH[..])
                    .unwrap();
                file.write_all(chunk_hash.1.to_string().as_bytes()).unwrap();
                file.write_all(b",").unwrap();
                file.write_all(format!("{}.chunk", i).as_bytes()).unwrap();
                file.write_all(b"\n").unwrap();
                i += 1;
            }
        }

        // save last chunk index
        let mut file = File::create(&self.STATE_PATH[..]).unwrap();
        file.write_all(i.to_string().as_bytes()).unwrap();

    }

    pub fn load_file(&self, path: &str) {
        let mut hashtochunk= checkifexist(&self.MAP1_PATH);
        let mut filetohashes = checkifexist(&self.MAP2_PATH);
        // println!("{:?}", hashtochunk);
        // println!("{:?}", filetohashes);
        
        let hashes = filetohashes.get(path).unwrap();
        // println!("{:?}", hashes);
        let mut chunks= Vec::new();
        for hash in hashes {
            // for hash get location of chunk
            // println!("{:?}", hash);      
            // println!("{:?}",hashtochunk.get(hash).unwrap()[0]);

            let mut file = File::open(format!("{}chunks/{}",self.DATABASE_PATH, hashtochunk.get(hash).unwrap()[0] )).unwrap();
            let mut buffer = Vec::new();
            file.read_to_end(&mut buffer).unwrap();
            chunks.push(buffer);
        }
        // return chunks
        let mut buffer = Vec::new();
        for chunk in chunks {
            buffer.extend(chunk);
        }
        let mut file = File::options()
            .write(true)
            .create(true)
            .open(path)
            .unwrap();
        file.write_all(&buffer).unwrap();
        // save file as file.txt
        // let mut file = File::create("file.jpeg").unwrap();
        // file.write_all(&buffer).unwrap();
    }

}

// fn main() {
//     println!("Hello, world!");

//     let CHUNK_SIZE: usize = 1024 *1024;

//     // constants: database path, state path, map1 path, map2 path
//     let DATABASE_PATH = "./database/";
//     // state path is database path + state
//     let STATE_PATH: &str = "./database/state";
//     // map1 path is database path + map1
//     let MAP1_PATH: &str = "./database/map1";
//     // map2 path is database path + map2
//     let MAP2_PATH: &str = "./database/map2";

//     let dedup : DedupManager = DedupManager{
//         CHUNK_SIZE,
//         DATABASE_PATH: DATABASE_PATH.to_string(),
//         STATE_PATH: STATE_PATH.to_string(),
//         MAP1_PATH: MAP1_PATH.to_string(),
//         MAP2_PATH: MAP2_PATH.to_string()
//     };

//     dedup.clear();
//     dedup.save_file("memory.txt");
//     dedup.save_file("mem.txt");
//     let mem = dedup.load_file(Path::new("mem.txt")).unwrap();
//     // save mem
//     let mut file = File::create("file.jpeg").unwrap();
//     file.write_all(&mem).unwrap();
//     // load_file(Path::new("index.jpeg")).unwrap();
// }
