#!/usr/bin/env runhaskell
import Text.Pandoc.JSON
import System.Process 
import Data.Digest.Pure.SHA 
import Data.ByteString.Lazy.UTF8 
import Data.List



img title infile outfile cmd contents = do
     writeFile infile contents
     e<-system cmd 
     appendFile "log" $ "exec: "++cmd++"\n"
     return (title,outfile)

code2img classes namevals contents=do
     let n=lookup "name" namevals 
         c=lookup "exec" namevals
         i=lookup "in" namevals
         o=lookup "out" namevals
     go n c i o
     where go (Just title) (Just cmd) (Just infile)(Just outfile)= img title infile outfile cmd contents
           go  _ _ _ _= do appendFile "log" "title/cmd is missing"
                           return undefined


transform::Block->IO Block
transform c@(CodeBlock (id, classes, namevals) contents) = do
 if ("img" `elem` classes )
 then do (name,outfile)<-code2img  classes namevals contents
         return $ Para [Image [(Str name)] (outfile, "")]
 else return c
transform c = return c

-- | Generate a unique filename given the file's contents.
uniqueName :: String -> String
uniqueName = ("tmp_"++).showDigest . sha1 . fromString

main :: IO ()
main = toJSONFilter $ transform 


