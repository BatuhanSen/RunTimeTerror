import React, { useState, Image, useRef, storageRef, imageRef, setFiles, useEffect } from "react";
import axios from "axios";
import Post from "./Post";
import ModifyPost from "./ModifyPost";

import { storage } from "../firebase";

const DisplayAllPosts = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [allPosts, setAllPosts] = useState([]);
  const [isCreateNewPost, setIsCreateNewPost] = useState(false);
  const [isModifyPost, setIsModifyPost] = useState(false);
  const [editPostId, setEditPostId] = useState("");

  const [image, setImage] = useState(null);
  const [url, setUrl] = useState("");
  const [progress, setProgress] = useState(0);

  const ReactFirebaseFileUpload = () => {

    const handleChange = e => {
      if (e.target.files[0]) {
        setImage(e.target.files[0]);
      }
    };

    const handleUpload = () => {
      const uploadTask = storage.ref(`images/${image.name}`).put(image);
      uploadTask.on(
        "state_changed",
        snapshot => {
          const progress = Math.round(
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100
          );
          setProgress(progress);
        },
        error => {
          console.log(error);
        },
        () => {
          storage
            .ref("images")
            .child(image.name)
            .getDownloadURL()
            .then(url => {
              setUrl(url);
            });
        }
      );
    };

    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <progress value={progress} max="100" />
        <br />
        <br />
        <input type="file" onChange={handleChange} />
        <button onClick={handleUpload}>Upload</button>
        <br />
        {url}
        <br />
        <img src={url || "http://via.placeholder.com/300"} alt="firebase-image" />
      </div>
    );
  };

  // Initialize useRef
  const getTitle = useRef();
  const getContent = useRef();
  const getUrl = useRef();

  const savePostTitleToState = event => {
    setTitle(event.target.value);
  };
  const savePostContentToState = event => {
    setContent(event.target.value);
  };
  const toggleCreateNewPost = () => {
    setIsCreateNewPost(!isCreateNewPost);
  };
  const toggleModifyPostComponent = () => {
    setIsModifyPost(!isModifyPost)
  }


  // GET REQUEST
  useEffect(() => {
    axios.get('https://how-to-survive.herokuapp.com/api/post', {
      headers: {
        Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inl1bnVzeXlrMzNAZ21haWwuY29tIiwidXNlcklkIjoiNjAzZDA0MzM0Mzg0ZmUwMDA0ZDZjZDMwIiwiaWF0IjoxNjE4NDQ1NzY4LCJleHAiOjE2MTg0NjczNjh9.CxelGuWxVVzV6ACXr8JVZY7F2zBkZ8QLBSZWEhBN8jI",
        'Content-type': 'application/json'
      },
    })
      .then(response => {
        setAllPosts(response.data.data.posts);
      })
  }, []);
  /////


  const editPost = _id => {
    setEditPostId(_id);
    toggleModifyPostComponent();
  };

  const deletePost = _id => {
    const modifiedPost = allPosts.filter(eachPost => {

      if (eachPost._id === _id) {

        ///// DELETE REQUEST
        fetch('https://how-to-survive.herokuapp.com/api/post/' + _id, {
          method: 'DELETE',
          mode: 'cors',
          headers: {
            'Content-Type': 'application/json',
            Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inl1bnVzeXlrMzNAZ21haWwuY29tIiwidXNlcklkIjoiNjAzZDA0MzM0Mzg0ZmUwMDA0ZDZjZDMwIiwiaWF0IjoxNjE4NDQ1NzY4LCJleHAiOjE2MTg0NjczNjh9.CxelGuWxVVzV6ACXr8JVZY7F2zBkZ8QLBSZWEhBN8jI"
          },
          body: JSON.stringify({
            postId: eachPost._id,
            userId: "603d04334384fe0004d6cd30",
          })
        })
          .then(async response => {
            try {
              const data = await response.text()
              console.log('response data?', data)
            } catch (error) {
              console.log('Error happened here!')
              console.error(error)
            }
          }
          )
        ///// 
      }
      return eachPost._id !== _id;
    });

    setAllPosts(modifiedPost);

  };

  const updatePost = (event) => {
    event.preventDefault();
    const updatedPost = allPosts.map(eachPost => {
      if (eachPost._id === editPostId) {

        /////// PUT REQUEST (UPDATE (EDIT) POST)
        fetch('https://how-to-survive.herokuapp.com/api/post/' + editPostId, {
          method: 'PUT',
          mode: 'cors',

          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inl1bnVzeXlrMzNAZ21haWwuY29tIiwidXNlcklkIjoiNjAzZDA0MzM0Mzg0ZmUwMDA0ZDZjZDMwIiwiaWF0IjoxNjE4NDQ0MTMyLCJleHAiOjE2MTg0NjU3MzJ9.ZP55jYlIRVpJEalNSc-zmJzIvRns8hXISos6DSjKJ9Y"
          },
          body: JSON.stringify({
            postId: editPostId,
            //userId: "603d04334384fe0004d6cd30",
            title: eachPost.title,
            content: eachPost.content,
            imageUrl: eachPost.url
          })
        })
          .then(async response => {
            try {
              const data = await response.text()
              console.log('response data?', data)
            } catch (error) {
              console.log('Error happened here!')
              console.error(error)
            }
          }

          )
        /////////////////////////

        return {
          ...eachPost,
          title: title || eachPost.title,
          content: content || eachPost.content
        };
      }
      return eachPost;
    });
    setAllPosts(updatedPost);

    toggleModifyPostComponent();
  };

  const savePost = event => {
    event.preventDefault();
    const _id = Date.now();
    setAllPosts([...allPosts, { _id, content, title, url }]);

    //// POST REQUEST
    fetch('https://how-to-survive.herokuapp.com/api/post', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inl1bnVzeXlrMzNAZ21haWwuY29tIiwidXNlcklkIjoiNjAzZDA0MzM0Mzg0ZmUwMDA0ZDZjZDMwIiwiaWF0IjoxNjE4NDQ1NzY4LCJleHAiOjE2MTg0NjczNjh9.CxelGuWxVVzV6ACXr8JVZY7F2zBkZ8QLBSZWEhBN8jI"
      },
      body: JSON.stringify({
        userId: "603d04334384fe0004d6cd30",
        title: title,
        content: content,
        imageUrl: url
      })
    })
      .then(res => {
        if (res.status === 422) {
          throw new Error('Validation failed.');
        }
        if (res.status !== 200 && res.status !== 201) {
          console.log('Error!');
          throw new Error('Could not authenticate you!');
        }
        console.log("AUTHENTICATED..");
        return res.json();
      })
    ////////

    setTitle("");
    setContent("");
    getTitle.current.value = "";
    getContent.current.value = "";
    toggleCreateNewPost();
  };


  if (isCreateNewPost) {
    return (
      <>
        <section className="create-post">
          <form onSubmit={savePost}>
            <h1>Create New Post</h1>
            <input
              type="text"
              onChange={savePostTitleToState}
              placeholder="Title"
              size="39"
              required
              ref={getTitle}
            ></input>
            <br />
            <br />
            <textarea
              onChange={savePostContentToState}
              placeholder="Content"
              rows="8"
              cols="41"
              required
              ref={getContent}
            ></textarea>
            <br />
            <br />
            <section className="button-wrapper">
              <button className="button">Save Post</button></section>
          </form>
        </section>
        <ReactFirebaseFileUpload />
      </>
    );
  }
  else if (isModifyPost) {
    const post = allPosts.find(post => {
      return post._id === editPostId;
    });
    return (
      <ModifyPost
        title={post.title}
        content={post.content}
        updatePost={updatePost}
        savePostTitleToState={savePostTitleToState}
        savePostContentToState={savePostContentToState}
      />
    );
  }

  return (
    <>
      {!allPosts.length ? (
        <section className="no-post">
          <h1>No Post Found!</h1>
          <h3>There is nothing to see here.</h3>
          <br />
          <br />
          <section className="button-wrapper">
            <button onClick={toggleCreateNewPost} className="button">Create New</button>
          </section>
        </section>
      ) : (
        <div><h1>All Posts</h1>
          <section className="all-post">
            {allPosts.map(eachPost => {
              return (
                <React.Fragment>
                  <Post
                    _id={eachPost._id}
                    key={eachPost._id}
                    title={eachPost.title}
                    content={eachPost.content}
                    imageUrl={eachPost.imageUrl}

                    editPost={editPost}
                    deletePost={deletePost}
                  />
                  <img src={eachPost.imageUrl || "https://via.placeholder.com/400x300"} alt="firebase-image" width="400" height="400" />
                </React.Fragment>
              );
            })}
            <section className="button-wrapper">
              <button onClick={toggleCreateNewPost} className="button">Create New</button>
            </section>
          </section>
        </div>
      )}

    </>
  );
};
export default DisplayAllPosts;